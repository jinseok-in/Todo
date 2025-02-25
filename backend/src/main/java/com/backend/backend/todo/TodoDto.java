package com.backend.backend.todo;

import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TodoDto {

    @NotBlank(message = "할 일 항목은 필수 항목입니다.")
    private String content;

    private String description;
    private Boolean isChecked;

    // Todo 마감 시간
    @NotBlank(message = "할 일 항목은 필수 항목입니다.")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
    private LocalDateTime deadline;
    
    public TodoDto(String content, String description, Boolean isChecked, LocalDateTime deadline) {
        this.content = content;
        this.description = description;
        this.isChecked = isChecked;
        this.deadline = deadline;
    }

    public Todo toEntity() {
        Todo todo = new Todo();
        todo.setContent(content);
        todo.setDescription(description);
        todo.setIsChecked(isChecked);
        todo.setDeadline(deadline);
        return todo;
    }

    public record IsCheckedUpdate(Boolean isChecked) {}
}
