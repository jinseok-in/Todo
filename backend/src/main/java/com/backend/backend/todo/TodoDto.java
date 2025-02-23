package com.backend.backend.todo;


import javax.validation.constraints.NotBlank;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TodoDto {

    @NotBlank(message = "할 일 항목은 필수 항목입니다.")
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
