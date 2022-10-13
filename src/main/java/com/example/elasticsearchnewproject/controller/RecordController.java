package com.example.elasticsearchnewproject.controller;

import com.example.elasticsearchnewproject.dto.RecordDto;
import com.example.elasticsearchnewproject.model.Record;
import com.example.elasticsearchnewproject.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class RecordController {
    private final RecordService service;

    @Autowired
    public RecordController(RecordService service) {
        this.service = service;
    }


    @GetMapping("/")
    public String showHomePage() {
        return "home_page";
    }

    @GetMapping("/newRecord")
    public String showNewRecordForm(Model model) {
        RecordDto recordDto = new RecordDto();
        model.addAttribute("recordDto", recordDto);
        return "new_record";
    }

//    @PostMapping("/saveRecord")
//    public String saveCustomer(@ModelAttribute RecordDto dto){
//        Record record = new Record();
//        record.setText(dto.getText());
//        record.setTitle(dto.getTitle());
//        service.saveRecord(record);
//        return "redirect:/";
//    }


    @PostMapping("/saveRecord")
    public String saveRecord(@ModelAttribute RecordDto recordDto) {
        service.saveRecord(service.dtoToRecord(recordDto));
        return "redirect:/";
    }

    @GetMapping("/recordsNonPag")
    public String findAllRecordsNonPag(Model model) {
        model.addAttribute("listOfRecords", service.findAllRecords());
        return "showAllRecords";
    }

    @GetMapping("/recordsPage")
    public String findAllRecords(Model model) {
        return findPaginated(1, model);
    }

    @GetMapping("/findByText")
    public String findByText(@RequestParam String text, Model model) {
        model.addAttribute("listOfRecordsByQuery", service.findByTextQuery(text));
        return "list_from_query";
    }

    @GetMapping("/page/{pageNumber}")
    public String findPaginated(@PathVariable(value = "pageNumber") int pageNumber, Model model) {
        int pageSize = 5;

        Page<Record> page = service.findPaginated(pageNumber, pageSize);
        List<Record> recordList = page.getContent();
        model.addAttribute("currentPage", pageNumber);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("listOfRecordsOnPage", recordList);
        return "showAllRecordsPage";
    }

    @GetMapping("/records")
    public String findRecordsPaginated(Pageable pageable, Model model) {
        Page<Record> page = service.findPaginated(pageable);
        model.addAttribute("page", page);
        return "showAllRecordsPage";
    }
}
