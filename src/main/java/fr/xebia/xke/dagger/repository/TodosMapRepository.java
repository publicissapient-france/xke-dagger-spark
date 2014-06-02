package fr.xebia.xke.dagger.repository;

import fr.xebia.xke.dagger.model.Todo;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Singleton
public class TodosMapRepository implements TodosRepository {


    private final Map<String, Todo> todos;

    public TodosMapRepository(List<Todo> todos) {
        this.todos= todos.stream().collect(Collectors.toMap(Todo::getId,  t -> t));
    }


    @Inject
    public TodosMapRepository(Map<String, Todo> todos) {
        this.todos= todos;
    }

    public Collection<Todo> getAll() {
        return todos.values();
    }

    public Todo save(Todo todo) {
        final Todo res;
        if(todo.getId() == null) {
            res = new Todo(UUID.randomUUID().toString(), todo.getTitle(), todo.getDescription());
        } else {
            res = todo;
        }

        todos.put(res.getId(), res);
        return res;
    }

    public Todo findById(String id) {
        return todos.get(id);
    }

    public Todo deleteById(String id) {
        return todos.remove(id);
    }
}
