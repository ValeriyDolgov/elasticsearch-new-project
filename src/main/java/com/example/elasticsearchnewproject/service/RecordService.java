package com.example.elasticsearchnewproject.service;

import com.example.elasticsearchnewproject.model.Record;
import org.springframework.data.domain.Page;

import java.util.List;

public interface RecordService {
    void saveRecord(Record record);
    Iterable<Record> findAllRecords();
    List<Record> findByTextQuery(String text);

    Page<Record> findPaginated(int pageNumber, int pageSize);
}
