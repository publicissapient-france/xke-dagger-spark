package fr.xebia.xke.dagger.controller;


import fr.xebia.xke.dagger.exception.NotFoundException;
import fr.xebia.xke.dagger.model.Todo;
import fr.xebia.xke.dagger.repository.TodosRepository;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Collection;

@Singleton
public class TodosController {

    private TodosRepository todosRepository;

    @Inject
    public TodosController(TodosRepository todosRepository) {
        this.todosRepository = todosRepository;
    }

    public Collection<Todo> getAll() {
        return todosRepository.getAll();
    }

    public Todo save(Todo todo) {
        return todosRepository.save(todo);
    }

    public Todo getById(String id) {
        Todo todo = todosRepository.findById(id);
        if (todo == null){
            throw new NotFoundException("Todo "+ id +" not found");
        }
        return todo;
    }

    public Todo delete(String id) {
        return todosRepository.deleteById(id);
    }
}
