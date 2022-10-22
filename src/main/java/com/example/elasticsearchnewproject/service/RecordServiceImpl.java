package com.example.elasticsearchnewproject.service;

import com.example.elasticsearchnewproject.constants.Strings;
import com.example.elasticsearchnewproject.dto.RecordDto;
import com.example.elasticsearchnewproject.model.Record;
import com.example.elasticsearchnewproject.repository.RecordRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;


@Service
public class RecordServiceImpl implements RecordService {

    private final RecordRepository repo;
    private final ModelMapper modelMapper;


    @Autowired
    public RecordServiceImpl(RecordRepository repo, ModelMapper modelMapper) {
        this.repo = repo;
        this.modelMapper = modelMapper;
    }

    @Override
    public void saveRecord(Record record) {
        this.repo.save(record);
    }

    @Override
    public Iterable<Record> findAllRecords() {

        return repo.findAll();
    }

    @Override
    public List<Record> findByTextQuery(String text) {
        return repo.findByText(text);
    }

    @Override
    public Page<Record> findPaginated(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return this.repo.findAll(pageable);
    }

    @Override
    public Page<Record> findPaginated(Pageable pageable) {

        return this.repo.findAll(pageable);
    }

    public Record dtoToRecord(RecordDto recordDto) {
        return modelMapper.map(recordDto, Record.class);
    }

    public RecordDto recordToDto(Record record) {

        return modelMapper.map(record, RecordDto.class);
    }

    public void stringToDom(String xmlSource) {
//        String xmlString = "<content><title>" + xmlSource + "</title></content>";
//        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
//        DocumentBuilder builder = factory.newDocumentBuilder();
//        return builder.parse(new InputSource(new StringReader(xmlSource)));
        String xmlStringTitle = null;
        String xmlStringContent = null;
        String[] titleAndText = xmlSource.split("\r\n", 2);
        for (int i = 0; i < titleAndText.length; i++) {
            if(i == 0){
               xmlStringTitle = "<content><title>" + titleAndText[0] + "</title>";
            } else xmlStringContent = "<content>" + titleAndText[1] + "</content></content>";
        }


        String xmlString = xmlStringTitle + xmlStringContent;
        try (FileWriter fileWriter = new FileWriter(Strings.PATH_TO_QUERY)) {
            fileWriter.write(xmlString);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveValid(String str) {
        stringToDom(str);
        File schemaFile = new File(Strings.PATH_TO_XSD);
        Source xmlFile = new StreamSource(new File(Strings.PATH_TO_QUERY));
        SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        DocumentBuilder documentBuilder;
        Document document;
        try {
            Schema schema = schemaFactory.newSchema(schemaFile);
            Validator validator = schema.newValidator();
            validator.validate(xmlFile);
        } catch (SAXException e) {
            System.out.println(xmlFile.getSystemId() + " is NOT valid reason: " + e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            document = documentBuilder.parse(new File(Strings.PATH_TO_QUERY));
        } catch (ParserConfigurationException | IOException | SAXException e) {
            throw new RuntimeException(e);
        }

        Node title = document.getDocumentElement().getFirstChild();
        Node content = document.getDocumentElement().getLastChild();
        String titleToRecord = title.getTextContent();
        String contentToRecord = content.getTextContent();
        RecordDto recordDto = new RecordDto();
        recordDto.setTitle(titleToRecord);
        recordDto.setContent(contentToRecord);
        this.repo.save(dtoToRecord(recordDto));
    }
}
