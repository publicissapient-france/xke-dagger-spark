package fr.xebia.xke.dagger.controller;


import fr.xebia.xke.dagger.model.Todo;
import fr.xebia.xke.dagger.repository.TodosMongoRepository;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Collection;

@Singleton
public class TodosController {

    private TodosMongoRepository todosMongoRepository;

    @Inject
    public TodosController(TodosMongoRepository todosMongoRepository) {
        this.todosMongoRepository = todosMongoRepository;
    }

    public Collection<Todo> getAll() {
        return todosMongoRepository.getAll();
    }

    public Todo save(Todo todo) {
        return todosMongoRepository.save(todo);
    }

    public Todo getById(String id) {
        return todosMongoRepository.findById(id);
    }
}
