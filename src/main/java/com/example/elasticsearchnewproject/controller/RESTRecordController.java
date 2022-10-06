package com.example.elasticsearchnewproject.controller;

import com.example.elasticsearchnewproject.model.Record;
import com.example.elasticsearchnewproject.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RESTRecordController {

    private final RecordService service;

    @Autowired
    public RESTRecordController(RecordService service) {
        this.service = service;
    }


    @PostMapping("/saveRecord")
    public void saveCustomer(@RequestBody Record record){
        service.saveRecord(record);
    }

    @GetMapping("/findAll")
    public Iterable<Record> findAllRecords(){
        return service.findAllRecords();
    }

    @GetMapping("/findByText/{text}")
    public List<Record> findByText(@PathVariable String text){
        return service.findByTextQuery(text);
    }
}
