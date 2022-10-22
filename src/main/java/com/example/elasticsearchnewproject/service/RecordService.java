package com.example.elasticsearchnewproject.service;

import com.example.elasticsearchnewproject.dto.RecordDto;
import com.example.elasticsearchnewproject.model.Record;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface RecordService {
    void saveRecord(Record record);

    Iterable<Record> findAllRecords();

    List<Record> findByTextQuery(String text);

    Page<Record> findPaginated(int pageNumber, int pageSize);

    Page<Record> findPaginated(Pageable pageable);

    Record dtoToRecord(RecordDto recordDto);

    RecordDto recordToDto(Record record);

    void stringToDom(String str);

    void saveValid(String str);


}
