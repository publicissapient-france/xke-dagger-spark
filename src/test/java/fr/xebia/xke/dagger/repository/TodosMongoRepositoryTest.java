package fr.xebia.xke.dagger.repository;

import com.mongodb.WriteResult;
import fr.xebia.xke.dagger.model.Todo;
import org.jongo.Find;
import org.jongo.FindOne;
import org.jongo.MongoCollection;
import org.jongo.Oid;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.Collection;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class TodosMongoRepositoryTest {
    
    private MongoCollection mongoCollection = Mockito.mock(MongoCollection.class);

    private TodosMongoRepository todosMongoRepository = new TodosMongoRepository(() -> mongoCollection);
    @Test
    public void should_delegate_to_mongo_collection_find() throws Exception {
        // Given
        Todo expectedTodo = Mockito.mock(Todo.class);

        Find findMock = mock(Find.class);
        when(findMock.as(Todo.class)).thenReturn(() -> Arrays.asList(expectedTodo).iterator());
        when(mongoCollection.find()).thenReturn(findMock);

        // When
        Collection<Todo> todos =  todosMongoRepository.getAll();

        // Then
        verify(mongoCollection, times(1)).find();
        assertThat(todos).containsOnly(expectedTodo);
    }

    @Test
    public void should_delegate_to_mongo_collection_find_with_id_parameter() throws Exception {
        // Given
        Todo expectedTodo = newTodo("1234");

        Find findMock = mock(Find.class);
        when(findMock.as(Todo.class)).thenReturn(() -> Arrays.asList(expectedTodo).iterator());
        when(mongoCollection.find(Oid.withOid(expectedTodo.getId()))).thenReturn(findMock);

        // When
        Todo todo =  todosMongoRepository.findById("1234");

        // Then
        assertThat(todo).isSameAs(expectedTodo);
    }


    @Test
    public void should_delegate_save_to_mongo_collection_and_then_retrieve_the_saved_todo() throws Exception {
        // Given
        Todo expectedTodo = newTodo("1234");

        FindOne findMock = mock(FindOne.class);
        when(findMock.as(Todo.class)).thenReturn(expectedTodo);
        when(mongoCollection.save(expectedTodo)).thenReturn(mock(WriteResult.class));
        when(mongoCollection.findOne(Oid.withOid("1234"))).thenReturn(findMock);

        // When
        Todo savedTodo = todosMongoRepository.save(expectedTodo);

        // Then
        verify(mongoCollection, times(1)).save(expectedTodo);
        assertThat(savedTodo).isSameAs(expectedTodo);
    }

    private Todo newTodo(String id) {
        return new Todo(id, "", null);
    }
}