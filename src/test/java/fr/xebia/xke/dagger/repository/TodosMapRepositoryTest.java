package fr.xebia.xke.dagger.repository;

import fr.xebia.xke.dagger.model.Todo;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static java.util.Arrays.asList;
import static org.fest.assertions.Assertions.assertThat;

public class TodosMapRepositoryTest {

    private final List<Todo> todos = new ArrayList<>();

    private TodosMapRepository todosMapRepository = new TodosMapRepository(todos);

    @Test
    public void should_retrieve_all_todos() throws Exception {
        // Given
        Todo expectedTodo = Mockito.mock(Todo.class);
        todosMapRepository = new TodosMapRepository(asList(expectedTodo));

        // When
        Collection<Todo> todos =  todosMapRepository.getAll();

        // Then
        assertThat(todos).hasSize(1);
        assertThat(todos).contains(expectedTodo);
    }

    @Test
    public void should_retrieve_todo_by_id() throws Exception {
        // Given
        Todo expectedTodo = newTodo("1234");
        todosMapRepository = new TodosMapRepository(asList(expectedTodo, newTodo("654"), newTodo("456788765")));

        // When
        Todo todo =  todosMapRepository.findById("1234");

        // Then
        assertThat(todo).isSameAs(expectedTodo);
    }

    @Test
    public void should_return_null_on_todo_by_bad_id() throws Exception {
        // Given
        Todo expectedTodo = newTodo("1234");
        todosMapRepository = new TodosMapRepository(asList(expectedTodo, newTodo("654"), newTodo("456788765")));

        // When
        Todo todo =  todosMapRepository.findById("BUBUUU");

        // Then
        assertThat(todo).isNull();
    }

    @Test
    public void should_save_new_todo_on_save_with_todo() throws Exception {
        // Given
        Todo expectedTodo = newTodo("1234");
        todosMapRepository = new TodosMapRepository(asList(newTodo("654"), newTodo("456788765")));

        // When
        todosMapRepository.save(expectedTodo);
        Todo todo =  todosMapRepository.findById("1234");

        // Then
        assertThat(todo).isSameAs(expectedTodo);
    }

    @Test
    public void should_update_todo_on_save_with_existing_id() throws Exception {
        // Given
        Todo expectedTodo = newTodo("1234");

        todosMapRepository = new TodosMapRepository(asList(newTodo("1234"), newTodo("654"), newTodo("456788765")));


        // When
        todosMapRepository.save(new Todo("1234", "Title ...", "Description"));
        Todo todo =  todosMapRepository.findById("1234");

        // Then
        assertThat(todo).isNotNull();
        assertThat(todo).isNotSameAs(expectedTodo);
        assertThat(todo.getTitle()).isEqualTo("Title ...");
        assertThat(todo.getDescription()).isEqualTo("Description");
    }


    @Test
    public void should_generate_id_on_save_without_id() throws Exception {
        // Given
        Todo expectedTodo = new Todo(null, "az", "dd");
        todosMapRepository = new TodosMapRepository(asList(newTodo("1234"), newTodo("654"), newTodo("456788765")));

        // When
        Todo saved = todosMapRepository.save(expectedTodo);
        Todo byId = todosMapRepository.findById(saved.getId());

        // Then
        assertThat(saved.getTitle()).isEqualTo("az");
        assertThat(saved.getDescription()).isEqualTo("dd");
        assertThat(saved.getId()).isNotNull();
        assertThat(saved).isEqualTo(byId);
    }

    private Todo newTodo(String id) {
        return new Todo(id, "", null);
    }

   /* asList(
            new Todo("1234", "Titre 1", "lorem ipsum"),
    new Todo("5432", "Titre 2", "lorem ipsum"),
    new Todo("08758", "Titre 3", "lorem ipsum")
    ).stream().collect(Collectors.toMap(Todo::getId, todo -> todo))*/


}