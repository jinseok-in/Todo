package com.backend.backend.todo;

import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
        return todoService.save(requestDto);
    }

    @PutMapping("/{id}")
    public String putMethodName(@PathVariable String id, @RequestBody String entity) {
        //TODO: process PUT request
        
        return entity;
    }
    
}
