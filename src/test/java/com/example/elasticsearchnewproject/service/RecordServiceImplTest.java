package com.example.elasticsearchnewproject.service;

import com.example.elasticsearchnewproject.model.Record;
import com.example.elasticsearchnewproject.repository.RecordRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
//public static void assertEquals(Object expected, Object actual) {
//        AssertEquals.assertEquals(expected, actual);
//        }
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
@RunWith(MockitoJUnitRunner.class)
@ExtendWith(MockitoExtension.class)
class RecordServiceImplTest {

    @Mock
    private RecordRepository repo;

    @InjectMocks
    private RecordServiceImpl service;
    //https://javarush.ru/quests/lectures/questservlets.level04.lecture00?ysclid=l9ohi89etx872630348
    //https://codefiction.net/unit-testing-crud-endpoints-of-a-spring-boot-java-web-service-api/
    @Test
    void saveRecord() {
        // Вкладываем данные и ожидаем 200 ОК
        Record record1 = new Record("eosjso;lje", "Test title No1","Test Text");
        Record recordWithoutID = new Record();
//        recordWithoutID.setTitle("Test title No2");
//        recordWithoutID.setText("Some text");
////        this.repo.save(record1);
//        assertEquals(repo.save(record1),);
//        when(repo.save(ArgumentMatchers.any(Record.class))).thenReturn(record1);
//        service.saveRecord(record1);
//        assertThat(service.saveRecord(record1));
        verify(repo).save(record1);
    }

    @Test
    void findAllRecords() {
    }

    @Test
    void findByTextQuery() {
    }
}