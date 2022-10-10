package com.example.elasticsearchnewproject.mapper;

import com.example.elasticsearchnewproject.dto.RecordDto;
import com.example.elasticsearchnewproject.model.Record;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class RecordConvertor {
    private final ModelMapper modelMapper;

    public RecordConvertor() {
        this.modelMapper = new ModelMapper();
    }

    public RecordDto converToDto(Record record) {
        return modelMapper.map(record, RecordDto.class);
    }

    public Record convertToModel(RecordDto recordDto) {
        return modelMapper.map(recordDto, Record.class);
    }
}
