package fr.xebia.xke.dagger.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class TodoDto {

    private final String id;

    private final String title;

    private final String description;

    @JsonCreator
    public TodoDto(@JsonProperty("id") String id, @JsonProperty("title") String title, @JsonProperty("description") String description) {
        this.id = id;
        this.title = title;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public static final TodoDto from(Todo todo) {
        return new TodoDto(todo.getId(), todo.getTitle(), todo.getDescription());
    }

    public final Todo to() {
        return new Todo(id, title, description);
    }
}
