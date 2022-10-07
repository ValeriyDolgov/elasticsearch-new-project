package com.example.elasticsearchnewproject.controller;

import com.example.elasticsearchnewproject.model.Record;
import com.example.elasticsearchnewproject.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class RecordController {

    private final RecordService service;

    @Autowired
    public RecordController(RecordService service) {
        this.service = service;
    }

    @GetMapping("/")
    public String showHomePage(){
        return "home_page";
    }

    @GetMapping("/showNewRecordForm")
    public String showNewRecordForm(Model model){
        Record record = new Record();
        model.addAttribute("record", record);
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
    public String saveRecord(@ModelAttribute Record record){
        service.saveRecord(record);
        return "redirect:/";
    }

    @GetMapping("/findAll")
    public Iterable<Record> findAllRecords(){

        return service.findAllRecords();
    }

    @GetMapping("/findByText/{text}")
    public String findByText(@PathVariable String text){
        service.findByTextQuery(text);
        return "list_from_query";
    }
}
