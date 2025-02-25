package com.backend.backend.todo;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class TodoService {

    private final TodoRepository todoRepository;

    // public TodoService(TodoRepository todoRepository) {
    //     this.todoRepository = todoRepository;
    // }

    public Optional<Todo> findById(Long id) {
        return todoRepository.findById(id);
    }

    // read todo (GET)
    public List<Todo> getAllTodo() {
        return todoRepository.findAllByOrderByDeadlineAsc();
    }

    // save todo (POST)
    public Long save(TodoDto requestDto) {
        return todoRepository.save(requestDto.toEntity()).getTodoId();
    }

    // update todo (PUT)
    // TodoDto를 매개변수로 받지 않고 Todo를 받아오기 떄문에 type을 Long이 아닌 Todo로 해야함.
    public Todo update(Long id, Todo todo) {
        return todoRepository.save(todo);
    }

    // delete todo (DELETE)
    public void delete(Long id) {
        todoRepository.deleteById(id);
    }

    // check update
    @Transactional
    public void checkUpdate(Long id, Boolean isChecked) {
        todoRepository.updateIsChecked(id, isChecked);
    }
}
