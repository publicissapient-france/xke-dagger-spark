package fr.xebia.xke.dagger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dagger.ObjectGraph;
import fr.xebia.xke.dagger.controller.TodosController;
import fr.xebia.xke.dagger.exception.BadRequestException;
import fr.xebia.xke.dagger.exception.InternalServerErrorException;
import fr.xebia.xke.dagger.exception.NotFoundException;
import fr.xebia.xke.dagger.model.Todo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Request;

import javax.inject.Inject;
import java.io.IOException;

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


    private void launch() {
        logger.info("launching server");

        get("/todos", (request, response) -> toJson(todosController.getAll()));

        get("/todos/:id", (request, response) -> toJson(todosController.getById(request.params("id"))));

        put("/todos", (request, response) -> {
            Todo savedTodo = todosController.save(parseTodoFromRequest(request));
            if (savedTodo == null) {
                throw new InternalServerErrorException("Error while saving");
            }
            return toJson(savedTodo);
        });

        delete("/todos/:id", (request, response) -> toJson( todosController.delete(request.params("id"))));

        exception(InternalServerErrorException.class, (e, request, response) -> response.status(500));

        exception(BadRequestException.class, (e, request, response) -> response.status(400));
        exception(NotFoundException.class, (e, request, response) -> {
            response.status(404);
            response.body(e.getMessage());
        });

        after((request, response) -> {
            response.header("Content-Type", "application/json");
        });
    }

    private String toJson(Object savedTodo) {
        try {
            return objectMapper.writeValueAsString(savedTodo);
        } catch (JsonProcessingException e) {
            throw new InternalServerErrorException(e);
        }
    }

    private Todo parseTodoFromRequest(Request request) {
        Todo todo;
        try {
            todo = objectMapper.readValue(request.body(), Todo.class);
        } catch (IOException e) {
            throw new BadRequestException(e);
        }
        return todo;
    }

    public static void main(String[] args) {
        ObjectGraph objectGraph = ObjectGraph.create(new TodosModule());
        objectGraph.get(SparkServer.class).launch();
    }

}
