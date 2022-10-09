package com.example.elasticsearchnewproject.service;

import com.example.elasticsearchnewproject.model.Record;
import com.example.elasticsearchnewproject.repository.RecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecordServiceImpl implements RecordService{

    private final RecordRepository repo;

    @Autowired
    public RecordServiceImpl(RecordRepository repo) {
        this.repo = repo;
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
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);
        return this.repo.findAll(pageable);
    }
}
