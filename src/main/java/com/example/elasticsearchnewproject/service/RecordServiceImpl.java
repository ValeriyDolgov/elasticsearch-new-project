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

import java.util.List;

@Service
public class RecordServiceImpl implements RecordService{

    private final RecordRepository repo;
    private final ModelMapper modelMapper;


    @Autowired
    public RecordServiceImpl(RecordRepository repo, ModelMapper modelMapper) {
        this.repo = repo;
        this.modelMapper = modelMapper;
//        modelMapper.createTypeMap(Record.class, RecordDto.class).addMapping(Record::getText, RecordDto::setContent);
//        modelMapper.createTypeMap(RecordDto.class, Record.class).addMapping(RecordDto::getContent, Record::setText);
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

    public Record dtoToRecord(RecordDto recordDto){
        return modelMapper.map(recordDto, Record.class);
    }

    public RecordDto recordToDto(Record record){

        return modelMapper.map(record, RecordDto.class);
    }
}
