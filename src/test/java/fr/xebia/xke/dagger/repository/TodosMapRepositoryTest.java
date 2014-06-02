package fr.xebia.xke.dagger.repository;

import fr.xebia.xke.dagger.model.Todo;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Collection;
import java.util.HashMap;

import static org.fest.assertions.Assertions.assertThat;

public class TodosMapRepositoryTest {

    private final HashMap<String, Todo> todos = new HashMap<>();

    private TodosMapRepository todosMapRepository = new TodosMapRepository(todos);

    @Test
    public void should_retrieve_all_todos() throws Exception {
        // Given
        Todo expectedTodo = Mockito.mock(Todo.class);
        todos.put("key1", expectedTodo);

        // When
        Collection<Todo> todos =  todosMapRepository.getAll();

        // Then
        assertThat(todos).hasSize(1);
        assertThat(todos).contains(expectedTodo);
    }
}