package com.example.elasticsearchnewproject.dto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

@Data
@JacksonXmlRootElement(
        namespace = "http://localhost:8080/",
        localName = "recordDto"
)
public class RecordDto {

    private String title;
    private String content;

}
