package com.example.elasticsearchnewproject.controller;

import com.example.elasticsearchnewproject.dto.RecordDto;
import com.example.elasticsearchnewproject.model.Record;
import com.example.elasticsearchnewproject.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RestRecordController {
    private final RecordService service;


    @Autowired
    public RestRecordController(RecordService service) {
        this.service = service;
    }

    @PostMapping("/rest/saveRecord")
    public void saveRecord(@ModelAttribute RecordDto recordDto) {
        service.saveRecord(service.dtoToRecord(recordDto));
    }

    @GetMapping("/rest/recordsNonPag")
    public Iterable<Record> findAllRecordsNonPag() {

        return service.findAllRecords();
    }

    @GetMapping("/rest/recordsPage")
    public List<Record> findAllRecords() {
        return findPaginated(1);
    }

    @GetMapping("/rest/findByText")
    public List<Record> findByText(@RequestParam String text) {
        return service.findByTextQuery(text);
    }

    @GetMapping("/rest/page/{pageNumber}")
    public List<Record> findPaginated(@PathVariable(value = "pageNumber") int pageNumber) {
        int pageSize = 5;

        Page<Record> page = service.findPaginated(pageNumber, pageSize);
        List<Record> content = page.getContent();
        return content;
    }
}
