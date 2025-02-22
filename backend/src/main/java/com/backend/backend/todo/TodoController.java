package com.backend.backend.todo;

import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
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
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class TodoController {

    private final TodoService todoService;
    
    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    // Mapping request Get Post
    @GetMapping
    public List<Todo> getAllTodo() {
        return todoService.getAllTodo();    
    }

    @PostMapping("/save")
    public Long save(@RequestBody TodoDto requestDto) {
        // TodoDto 형식
        return todoService.save(requestDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Todo> updateTodo(@PathVariable("id") Long id, @RequestBody TodoDto todoDto) {
        
        Todo existingTodo = todoService.findById(id)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "데이터를 찾을 수 없습니다."));
        
        existingTodo = existingTodo.toBuilder()
                    .content(todoDto.getContent())
                    .description(todoDto.getDescription())
                    .isChecked(todoDto.getIsChecked())
                    .build();
        
        Todo updateTodo = todoService.update(id, existingTodo);

        return ResponseEntity.ok(updateTodo);
    }
    
}
