package com.example.elasticsearchnewproject.repository;

import com.example.elasticsearchnewproject.model.Record;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecordRepository extends ElasticsearchRepository<Record, String> {
    List<Record> findByText(String text);
}
