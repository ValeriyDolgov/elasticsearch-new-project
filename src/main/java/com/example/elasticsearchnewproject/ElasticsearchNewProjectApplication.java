package com.example.elasticsearchnewproject;

import com.example.elasticsearchnewproject.dto.RecordDto;
import com.example.elasticsearchnewproject.model.Record;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ElasticsearchNewProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(ElasticsearchNewProjectApplication.class, args);
    }

    @Bean
    public ModelMapper modelMapper(){
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.createTypeMap(Record.class, RecordDto.class).addMapping(Record::getText, RecordDto::setContent);
        modelMapper.createTypeMap(RecordDto.class, Record.class).addMapping(RecordDto::getContent, Record::setText);
        return modelMapper;
    }

}
