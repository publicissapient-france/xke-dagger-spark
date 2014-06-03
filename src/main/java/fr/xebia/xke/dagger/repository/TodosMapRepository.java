package fr.xebia.xke.dagger.repository;

import fr.xebia.xke.dagger.model.Todo;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TodosMapRepository implements TodosRepository {


    private final Map<String, Todo> todos;

    public TodosMapRepository(List<Todo> todos) {
        this.todos = todos.stream().collect(Collectors.toMap(Todo::getId, t -> t));
    }


    public TodosMapRepository(Map<String, Todo> todos) {
        this.todos = todos;
    }

    public Collection<Todo> getAll() {
        return null;
    }

    public Todo save(Todo todo) {
        return null;
    }

    public Todo findById(String id) {
        return null;
    }

    public Todo deleteById(String id) {
        return null;
    }
}
