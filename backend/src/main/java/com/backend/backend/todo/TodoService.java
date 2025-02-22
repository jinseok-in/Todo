package com.backend.backend.todo;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class TodoService {

    private final TodoRepository todoRepository;

    // public TodoService(TodoRepository todoRepository) {
    //     this.todoRepository = todoRepository;
    // }

    // read todo (GET)
    public List<Todo> getAllTodo() {
        return todoRepository.findAll();
    }

    // save todo (POST)
    public Long save(TodoDto requestDto) {
        return todoRepository.save(requestDto.toEntity()).getTodoId();
    }

    // update todo (PUT)

    // delete todo (DELETE)

}
