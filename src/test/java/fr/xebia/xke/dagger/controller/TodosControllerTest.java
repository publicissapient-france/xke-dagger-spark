package fr.xebia.xke.dagger.controller;

import fr.xebia.xke.dagger.repository.TodosRepository;
import org.junit.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class TodosControllerTest {

    private TodosRepository todosRepository = Mockito.mock(TodosRepository.class);
    private TodosController todosController = new TodosController(todosRepository);

    @Test
    public void should_delegate_get_all_to_repository() throws Exception {
        // Given & When
        todosController.getAll();

        // Then
        verify(todosRepository, times(1)).getAll();
    }
}