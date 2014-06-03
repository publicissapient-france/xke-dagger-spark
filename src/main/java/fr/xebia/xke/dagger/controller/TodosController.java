package fr.xebia.xke.dagger.controller;


import fr.xebia.xke.dagger.model.Todo;
import fr.xebia.xke.dagger.repository.TodosRepository;

import java.util.Collection;

public class TodosController {

    private TodosRepository todosRepository;

    public TodosController(TodosRepository todosRepository) {
        this.todosRepository = todosRepository;
    }

    public Collection<Todo> getAll() {
        return null;
    }

    public Todo save(Todo todo) {
        return null;
    }

    public Todo getById(String id) {
        return null;
    }

    public Todo delete(String id) {
        return null;
    }
}
