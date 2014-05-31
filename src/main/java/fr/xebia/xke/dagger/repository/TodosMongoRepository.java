package fr.xebia.xke.dagger.repository;

import com.google.common.collect.Lists;
import fr.xebia.xke.dagger.model.Todo;
import org.jongo.MongoCollection;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Collection;

import static org.jongo.Oid.withOid;

@Singleton
public class TodosMongoRepository implements TodosRepository {

    private final MongoCollection todosMongoCollection;

    @Inject
    public TodosMongoRepository(MongoCollection todosMongoCollection) {
        this.todosMongoCollection = todosMongoCollection;
    }

    public Collection<Todo> getAll() {
        return Lists.newArrayList(todosMongoCollection.find().as(Todo.class));
    }

    public Todo save(Todo todo) {
        todosMongoCollection.save(todo);
        return todosMongoCollection.findOne(withOid(todo.getId())).as(Todo.class);
    }

    public Todo findById(String id) {
        return todosMongoCollection.findOne(withOid(id)).as(Todo.class);
    }

    public Todo deleteById(String id) {
        return todosMongoCollection.findAndModify(withOid(id)).remove().as(Todo.class);
    }
}
