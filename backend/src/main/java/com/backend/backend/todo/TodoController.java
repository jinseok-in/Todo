package com.backend.backend.todo;

import java.util.List;
import org.slf4j.Logger;

import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/api/todos")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class TodoController {

    private final TodoService todoService;

    private static final Logger logger = LoggerFactory.getLogger(TodoController.class);
    
    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    // Mapping request Get Post
    @GetMapping
    public List<Todo> getAllTodo() {
        logger.info("GET /api/todos check");
        System.out.println("Get /api/todos check");
        return todoService.getAllTodo();    
    }
    
}
