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
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.fail;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TodoControllerTest {

    @LocalServerPort
    private int port;

    // 서버 실행 및 요청 테스트
    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private TodoService todoService;

    // save 성공 케이스
    @Test
    void saveSuccessTest() {
        // given
        String content = "Test Content";
        String description = "Test Description";
        Boolean isChecked = false;
        LocalDateTime deadline = LocalDateTime.parse("2025-03-01 14:30", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

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
        String content = "Test Content";
        String description = "Test Description";
        Boolean isChecked = false;
        LocalDateTime deadline = LocalDateTime.parse("2025-03-01 14:30", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

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
        String content = "Test Content";
        String description = "Test Description";
        Boolean isChecked = false;
        LocalDateTime deadline = LocalDateTime.parse("2025-03-01 14:30", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

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
        // given
        String content = "Test Update Content";
        String description = "Test Update Description";
        Boolean isChecked = false;
        LocalDateTime deadline = LocalDateTime.parse("2025-03-01 14:30", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

        // when
        List<Todo> todoList = todoService.getAllTodo();

        if (!todoList.isEmpty()) {
            // then
            Todo data = todoList.get(0);
        
            data.setContent("Test Content");
            data.setDescription(description);
            data.setIsChecked(isChecked);
            data.setDeadline(deadline);
        
            Todo update = todoService.update(data.getTodoId(), data);
        
            assertThat(update).isNotNull();
            assertThat(update.getContent()).isEqualTo("Test Content");
            assertThat(update.getDescription()).isEqualTo(description);
            assertThat(update.getIsChecked()).isEqualTo(isChecked);
            assertThat(update.getDeadline()).isEqualTo(deadline);
        } else {
            // 데이터가 없으면 새로운 데이터 추가 후 업데이트
            TodoDto newTodoDto = new TodoDto("New Content", "New Description", false,
                    LocalDateTime.parse("2025-03-01 14:30", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        
            Long newTodoId = todoService.save(newTodoDto);
        
            Todo newTodo = todoService.findById(newTodoId)
                    .orElseThrow(() -> new RuntimeException("새로운 데이터를 찾을 수 없습니다."));
        
            // 업데이트 수행
            newTodo.setContent("Updated Content");
            newTodo.setDescription("Updated Description");
            newTodo.setIsChecked(true);
            newTodo.setDeadline(LocalDateTime.parse("2025-03-02 16:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        
            Todo updatedTodo = todoService.update(newTodo.getTodoId(), newTodo);
        
            // 검증
            assertThat(updatedTodo).isNotNull();
            assertThat(updatedTodo.getContent()).isEqualTo("Updated Content");
            assertThat(updatedTodo.getDescription()).isEqualTo("Updated Description");
            assertThat(updatedTodo.getIsChecked()).isTrue();
            assertThat(updatedTodo.getDeadline()).isEqualTo(LocalDateTime.parse("2025-03-02 16:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        }

    }
    

    // TODO : PUT (update) 실패 케이스
    // update 실패 케이스
    @Test
    void updateFailTest() {
        // given
        String content = ""; // 빈 값으로 설정 (업데이트 실패 유도)
        String description = "Test Update Description";
        Boolean isChecked = false;
        LocalDateTime deadline = LocalDateTime.parse("2025-03-01 14:30", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    
        // when
        List<Todo> todoList = todoService.getAllTodo();
    
        if (!todoList.isEmpty()) {
            // 데이터가 존재하면 첫 번째 데이터를 가져와 업데이트 수행
            Todo data = todoList.get(0);
    
            data.setContent(content); // 실패 조건: content가 빈 문자열
            data.setDescription(description);
            data.setIsChecked(isChecked);
            data.setDeadline(deadline);
    
            try {
                Todo update = todoService.update(data.getTodoId(), data);
                fail("업데이트가 실패해야 하지만 성공했습니다.");
            } catch (Exception e) {
                System.out.println("업데이트 실패 확인: " + e.getMessage());
                assertThat(e.getMessage()).contains("할 일의 내용은 필수 항목입니다.");
            }
        } else {
            // 데이터가 없으면 새 데이터 추가 후 업데이트 실패 시도
            TodoDto newTodoDto = new TodoDto("Valid Content", "New Description", false,
                    LocalDateTime.parse("2025-03-01 14:30", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
    
            Long newTodoId = todoService.save(newTodoDto);
    
            Todo newTodo = todoService.findById(newTodoId)
                    .orElseThrow(() -> new RuntimeException("새로운 데이터를 찾을 수 없습니다."));
    
            // 실패할 데이터를 설정 (content를 빈 문자열로 변경)
            newTodo.setContent(content);
            newTodo.setDescription(description);
            newTodo.setIsChecked(isChecked);
            newTodo.setDeadline(deadline);
    
            try {
                Todo update = todoService.update(newTodo.getTodoId(), newTodo);
                fail("업데이트가 실패해야 하지만 성공했습니다.");
            } catch (Exception e) {
                System.out.println("업데이트 실패 확인: " + e.getMessage());
                assertThat(e.getMessage()).contains("할 일의 내용은 필수 항목입니다.");
            }
        }
    }

    // TODO : DELETE (delete) 성공 케이스
    // delete 성공 케이스
    @Test
    void deleteSuccessTest() {
        // given
        String content = "Test Content";
        String description = "Test Description";
        Boolean isChecked = false;
        LocalDateTime deadline = LocalDateTime.parse("2025-03-01 14:30", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    
        // when
        List<Todo> todoList = todoService.getAllTodo();
    
        if (!todoList.isEmpty()) {
            // 데이터가 있으면 첫 번째 데이터 삭제
            Todo data = todoList.get(0);
            todoService.delete(data.getTodoId());
    
            // then
            Optional<Todo> deletedTodo = todoService.findById(data.getTodoId());
            assertThat(deletedTodo).isEmpty();
        } else {
            // 데이터가 없으면 새로 추가 후 삭제
            TodoDto newTodoDto = new TodoDto(content, description, isChecked, deadline);
            Long newTodoId = todoService.save(newTodoDto);
    
            Todo newTodo = todoService.findById(newTodoId)
                    .orElseThrow(() -> new RuntimeException("새로운 데이터를 찾을 수 없습니다."));
    
            todoService.delete(newTodo.getTodoId());
    
            // then
            Optional<Todo> deletedTodo = todoService.findById(newTodo.getTodoId());
            assertThat(deletedTodo).isEmpty();
        }
    }

    // TODO : DELETE (delete) 실패 케이스
    // delete 실패 케이스
    @Test
    void deleteFailTest() {
        // given
        Long nonExistentId = 9999L;
        String url = "http://localhost:" + port + "/api/todos/delete/" + nonExistentId;
    
        // when
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);
    
        // then
        System.out.println("response : " + responseEntity.getBody());
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

}
