package fr.xebia.xke.dagger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.xebia.xke.dagger.controller.TodosController;
import fr.xebia.xke.dagger.exception.BadRequestException;
import fr.xebia.xke.dagger.exception.InternalServerErrorException;
import fr.xebia.xke.dagger.exception.NotFoundException;
import fr.xebia.xke.dagger.model.TodoDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Request;
import spark.ResponseTransformer;

import java.io.IOException;

import static spark.Spark.*;

public class SparkServer {

    private static Logger logger = LoggerFactory.getLogger(SparkServer.class);

    private final TodosController todosController;
    private final ObjectMapper objectMapper;
    private final ResponseTransformer jsonTransformer;

    public SparkServer(TodosController todosController, ObjectMapper objectMapper) {
        this.todosController = todosController;
        this.objectMapper = objectMapper;
        this.jsonTransformer = (ResponseTransformer) model -> toJson(model);
    }


    void launch(int port) {
        logger.info("launching server");

        setPort(port);

        staticFileLocation("/public");

        after((request, response) -> response.type("application/json"));

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
    }

}
