package fr.xebia.xke.dagger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dagger.ObjectGraph;
import fr.xebia.xke.dagger.controller.TodosController;
import fr.xebia.xke.dagger.exception.BadRequestException;
import fr.xebia.xke.dagger.exception.InternalServerErrorException;
import fr.xebia.xke.dagger.exception.NotFoundException;
import fr.xebia.xke.dagger.model.Todo;
import fr.xebia.xke.dagger.model.TodoDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Request;

import javax.inject.Inject;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static spark.Spark.*;

public class SparkServer {

    private static Logger logger = LoggerFactory.getLogger(SparkServer.class);

    private final TodosController todosController;
    private final ObjectMapper objectMapper;

    @Inject
    public SparkServer(TodosController todosController, ObjectMapper objectMapper) {
        this.todosController = todosController;
        this.objectMapper = objectMapper;
    }


    void launch(int port) {
        logger.info("launching server");

        setPort(port);

        staticFileLocation("/public");

        after((request, response) -> response.type("application/json"));

        get("/todos", (request, response) -> {
            List<TodoDto> todoDtos = todosController.getAll().stream().map(TodoDto::from).collect(Collectors.<TodoDto>toList());
            return toJson(todoDtos);
        });

        get("/todos/:id", (request, response) -> toJson(TodoDto.from(todosController.getById(request.params("id")))));

        put("/todos", (request, response) -> {
            TodoDto todoDto = parseTodoFromRequest(request);
            Todo savedTodo = todosController.save(todoDto.to());
            if (savedTodo == null) {
                throw new InternalServerErrorException("Error while saving");
            }
            return toJson(TodoDto.from(savedTodo));
        });

        delete("/todos/:id", (request, response) -> toJson(todosController.delete(request.params("id"))));

        exception(InternalServerErrorException.class, (e, request, response) -> response.status(500));

        exception(BadRequestException.class, (e, request, response) -> response.status(400));
        exception(NotFoundException.class, (e, request, response) -> {
            response.status(404);
            response.body(e.getMessage());
        });
    }

    private String toJson(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new InternalServerErrorException(e);
        }
    }

    private TodoDto parseTodoFromRequest(Request request) {
        TodoDto todoDto;
        try {
            todoDto = objectMapper.readValue(request.body(), TodoDto.class);
        } catch (IOException e) {
            throw new BadRequestException(e);
        }
        return todoDto;
    }

    public static void main(String[] args) {
        ObjectGraph objectGraph = ObjectGraph.create(new TodosModule());
        objectGraph.get(SparkServer.class).launch(4567);
    }

}
