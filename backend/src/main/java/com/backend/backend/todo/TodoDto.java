package com.backend.backend.todo;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TodoDto {

    private String content;
    private String description;
    private Boolean isChecked;

    @Builder
    public TodoDto(String content, String description, Boolean isChecked) {
        this.content = content;
        this.description = description;
        this.isChecked = isChecked;
    }

    public Todo toEntity() {
        return Todo.builder()
            .todoId(null)
            .content(content)
            .description(description)
            .isChecked(isChecked)
            .build();
    }
}
