package com.backend.backend.todo;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import static org.assertj.core.api.Assertions.assertThat;

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

        String url = "http://localhost:" + port + "/api/todos/save";

        TodoDto todoDto = TodoDto.builder()
                            .content(content)
                            .description(description)
                            .isChecked(isChecked)
                            .build();

        // when
        ResponseEntity<Long> responseEntity = restTemplate.postForEntity(url, todoDto, Long.class);

        // then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);

        // 데이터베이스에서 확인
        List<Todo> all = restTemplate.getForObject("http://localhost:" + port + "/api/todos", List.class);
        assertThat(all).isNotEmpty();
    }

    // save 실패 케이스
    // @Test saveFailTest() {

    // }
}
