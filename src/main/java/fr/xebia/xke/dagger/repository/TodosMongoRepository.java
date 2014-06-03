package fr.xebia.xke.dagger.repository;

import com.google.common.collect.Lists;
import dagger.Lazy;
import fr.xebia.xke.dagger.model.Todo;
import org.jongo.MongoCollection;

import java.util.Collection;

import static org.jongo.Oid.withOid;

public class TodosMongoRepository implements TodosRepository {

    private final Lazy<MongoCollection> todosMongoCollection;

    public TodosMongoRepository(Lazy<MongoCollection> todosMongoCollection) {
        this.todosMongoCollection = todosMongoCollection;
    }

    public Collection<Todo> getAll() {
        return Lists.newArrayList(collection().find().as(Todo.class));
    }

    public Todo save(Todo todo) {
        collection().save(todo);
        return collection().findOne(withOid(todo.getId())).as(Todo.class);
    }

    private MongoCollection collection() {
        return todosMongoCollection.get();
    }

    public Todo findById(String id) {
        return collection().findOne(withOid(id)).as(Todo.class);
    }

    public Todo deleteById(String id) {
        return collection().findAndModify(withOid(id)).remove().as(Todo.class);
    }
}
