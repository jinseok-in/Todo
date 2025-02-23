package com.backend.backend.todo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TodoControllerTest {

    @LocalServerPort
    private int port;

    // 서버 실행 및 요청 테스트
    @Autowired
    private TestRestTemplate restTemplate;

    // save 성공 케이스
    @Test
    void saveSuccessTest() {
        // given
        String content = "컴퓨터 켜기";
        String description = "업무 시작 시 컴퓨터 부터 켜기";
        Boolean isChecked = true;
        LocalDateTime deadline = LocalDateTime.parse("2025-03-01 14:30", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));;

        String url = "http://localhost:" + port + "/api/todos/save";

        TodoDto todoDto = new TodoDto(content, description, isChecked, deadline);

        // when
        ResponseEntity<Long> responseEntity = restTemplate.postForEntity(url, todoDto, Long.class);

        // then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);

    }

    @Test
    void saveNoDescriptionSuccessTest() {
        // given
        String content = "앉기";
        String description = null;
        Boolean isChecked = false;
        LocalDateTime deadline = LocalDateTime.parse("2025-03-01 14:30", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));;

        String url = "http://localhost:" + port + "/api/todos/save";

        TodoDto todoDto = new TodoDto(content, description, isChecked, deadline);

        // when
        ResponseEntity<Long> responseEntity = restTemplate.postForEntity(url, todoDto, Long.class);

        // then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);

    }

    // save 실패 케이스
    @Test 
    void saveFailContentNullTest() {
        // given
        String content = null;
        String description = "test";
        Boolean isChecked = false;
        LocalDateTime deadline = LocalDateTime.parse("2025-03-01 14:30", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));;

        String url = "http://localhost:" + port + "/api/todos/save";
        
        TodoDto todoDto = new TodoDto(content, description, isChecked, deadline);

        // when
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, todoDto, String.class);

        // then
        System.out.println("response : " + responseEntity.getBody());
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);

    }

    // TODO : PUT (update) 성공 케이스
    // update 성공 케이스
    @Test
    void updateSuccessTest() {

    }
    

    // TODO : PUT (update) 실패 케이스
    // update 실패 케이스


    // TODO : DELETE (delete) 성공 케이스
    // delete 성공 케이스
    @Test
    void deleteSuccessTest() {

    }

    // TODO : DELETE (delete) 실패 케이스
    // delete 실패 케이스


}
