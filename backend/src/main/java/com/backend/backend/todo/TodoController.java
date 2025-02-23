package com.backend.backend.todo;

import java.util.List;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;




@RestController
@RequestMapping("/api/todos")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class TodoController {

    private final TodoService todoService;

    // Mapping request Get Post
    @GetMapping
    public List<Todo> getAllTodo() {
        return todoService.getAllTodo();    
    }

    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody @Valid TodoDto requestDto) {
        if (requestDto.getContent() == null || requestDto.getContent().trim().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("content는 필수 입력값입니다.");
        }
        // TodoDto 형식
        Long savedId = todoService.save(requestDto);
        return ResponseEntity.ok(savedId);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Todo> updateTodo(@PathVariable("id") Long id, @RequestBody TodoDto todoDto) {
        
        Todo existingTodo = todoService.findById(id)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "데이터를 찾을 수 없습니다."));
        
        existingTodo.setContent(todoDto.getContent());
        existingTodo.setDescription(todoDto.getDescription());
        existingTodo.setIsChecked(todoDto.getIsChecked());
        
        Todo updateTodo = todoService.update(id, existingTodo);

        return ResponseEntity.ok(updateTodo);
    }

    @GetMapping("/delete/{id}")
    public ResponseEntity<Void> deleteTodo(@PathVariable("id") Long id) {

        Todo todo = todoService.findById(id)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "데이터를 찾을 수 없습니다."));
        
        todoService.delete(todo.getTodoId());

        return ResponseEntity.noContent().build();
    }
    
}
