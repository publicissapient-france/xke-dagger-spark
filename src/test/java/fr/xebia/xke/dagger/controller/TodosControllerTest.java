package fr.xebia.xke.dagger.controller;

import fr.xebia.xke.dagger.exception.NotFoundException;
import fr.xebia.xke.dagger.model.Todo;
import fr.xebia.xke.dagger.repository.TodosRepository;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.stubbing.OngoingStubbing;

import static org.fest.assertions.Assertions.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class TodosControllerTest {

    private TodosRepository todosRepository = Mockito.mock(TodosRepository.class);
    private TodosController todosController = new TodosController(todosRepository);

    @Test
    public void should_delegate_get_all_to_repository() throws Exception {
        // Given & When
        todosController.getAll();

        // Then
        verify(todosRepository).getAll();
    }



    @Test
    public void should_delegate_get_todo_id_to_repository(){

        // Given
        Todo todo = new Todo("", null, null);

        when(todosRepository.findById("1234")).thenReturn(todo);

        Todo res = todosController.getById("1234");

        assertThat(res).isSameAs(todo);

        // Then
        verify(todosRepository).findById("1234");
    }


    @Test(expected = NotFoundException.class)
    public void should_throw_not_found_on_get_todo_bad_id(){

        // Given
        when(todosRepository.findById("1234")).thenReturn(null);

        // When
        Todo res = todosController.getById("1234");

        // Then expect NotFound
        fail();
    }


}