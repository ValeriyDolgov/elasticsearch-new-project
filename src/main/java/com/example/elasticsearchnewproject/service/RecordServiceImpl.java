package com.example.elasticsearchnewproject.service;

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
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
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

    public static Document stringToDom(String xmlSource) throws SAXException, ParserConfigurationException, IOException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        return builder.parse(new InputSource(new StringReader(xmlSource)));
    }
    public void saveValid(String str){ // Если верить postman- сохраняется только полу title. Упрощает работу, не надо
        // разделять String
        //Сначала запиши строку в файл xml, считай или провалидируй его через xsd. Если ответ хороший, то отправь в ES.
        //https://stackoverflow.com/questions/15732/how-to-validate-an-xml-file-against-an-xsd-file
        Source xmlFile = new StreamSource(new File("test.xml"));
    }
}
