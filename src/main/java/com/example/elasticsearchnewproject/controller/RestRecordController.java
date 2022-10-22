package com.example.elasticsearchnewproject.controller;

import com.example.elasticsearchnewproject.model.Record;
import com.example.elasticsearchnewproject.service.RecordService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")

public class RestRecordController {
    private final RecordService service;


    @Autowired
    public RestRecordController(RecordService service) {
        this.service = service;
    }

    @Operation(summary = "Перевести из ДТО в модель и сохранить в ElasticSearch" +
            "(Convert DTO to Model and save it to the ElasticSearch)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Запись сохранена(Record is saved)",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Record.class))}),
            @ApiResponse(responseCode = "400", description = "Какое-то из полей заполнено не правильно!" +
                    "(Some of fields of Record may filled incorrectly)",
                    content = @Content),
            @ApiResponse
    })
    @PostMapping(
            value = "/save-xml",
            consumes = "application/xml"
    )
    public void saveRecord(@RequestBody String str) {
        service.saveValid(str);
    }

    @Operation(summary = "Получить записи без пагинации(Getting records without pagination)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Выводится список записей(Showed list of records)")
    })
    @GetMapping(
            value = "/xml-records",
            produces = "application/xml"
    )
    public Iterable<Record> findAllRecordsNonPag() {
        return service.findAllRecords();
    }

    @Operation(summary = "Получить записи с пагинацией(Getting records with pagination)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Выводится первая страница с 5 элементами" +
                    "(Showed list with 5 records)")
    })
    @GetMapping("/recordsPage")
    public List<Record> findAllRecords() {
        return findPaginated(1);
    }

    @Operation(summary = "Найти запись по текстовому запросу(Get record by text query)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Выводит список всех элементов, найденных по запросу" +
                    "(Showed list of elements, find by text query)")
    })
    @GetMapping("/findByText")
    public List<Record> findByText(@RequestParam String text) {
        return service.findByTextQuery(text);
    }

    @Operation(summary = "Открывается страница с пагинацией, сделанной через Pageable. Количество элементов на странице " +
            "- 5(Opened page with pagination, created default by Pageable instance. 5 - number of elements on page)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Выводится список из 5 записей(Opened page with 5 elements)")
    })
    @GetMapping("/page/{pageNumber}")
    public List<Record> findPaginated(@PathVariable(value = "pageNumber") int pageNumber) {
        int pageSize = 5;

        Page<Record> page = service.findPaginated(pageNumber, pageSize);
        return page.getContent();
    }
}
