package com.example.elasticsearchnewproject.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName = "records")
@Data //Заменяет @Getter и Setter, toString, @RequiredArgsConstructor, @EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class Record {

    @Id
    private String id;

    private String title;

    private String text;
}
