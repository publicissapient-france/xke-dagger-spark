package fr.xebia.xke.dagger;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import dagger.ObjectGraph;
import fr.xebia.xke.dagger.model.TodoDto;
import org.hamcrest.CoreMatchers;
import org.junit.*;
import spark.Spark;

import static com.jayway.restassured.RestAssured.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;

public class SparkServerTest {

    public static final int SPARK_PORT = 4667;

    @BeforeClass
    public static void setUp() throws Exception {
        ObjectGraph objectGraph = ObjectGraph.create(new TodosInMemoryModule());
        objectGraph.get(SparkServer.class).launch(SPARK_PORT);
        port = SPARK_PORT;
    }

    @Test
    public void should_get_all_todos() throws Exception {
        given()
                .when().
                    get("/todos")
                .then().log().all().
                assertThat().body("id", hasSize(3));
    }


    @Test
    public void server_should_get_todo_with_id() throws Exception {
        given()
                .contentType(ContentType.JSON).
        when().
                get("/todos/1234").
        then().log().all().
                assertThat().body("id", is("1234"));
    }

    @Test
    public void server_should_respond_404_on_get_todo_with_bad_id() throws Exception {
        given()
                .contentType(ContentType.JSON).
        when().
                get("/todos/1234556").
        then().log().all().
                assertThat().statusCode(404);
    }

    @Test
    public void server_should_save_new_todo_on_put_todos_with_todo() throws Exception {
        given()
                .contentType(ContentType.JSON).and().
                content(new TodoDto("12344566543", "Test title", "description")).
        when().
                put("/todos").
        then().log().all().
                assertThat().statusCode(200).and().
                assertThat().body("id", is("12344566543")).
                assertThat().body("description", is("description")).
                assertThat().body("title", is("Test title"));

        // Post clean DB
        delete("/todos/12344566543").then().
                assertThat().statusCode(200);
    }

    @AfterClass
    public static void tearDown() throws Exception {
        Spark.stop();
    }
}
