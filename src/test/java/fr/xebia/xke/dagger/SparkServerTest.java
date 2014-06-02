package fr.xebia.xke.dagger;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import dagger.ObjectGraph;
import org.hamcrest.CoreMatchers;
import org.junit.*;
import spark.Spark;

import static com.jayway.restassured.RestAssured.given;
import static com.jayway.restassured.RestAssured.port;
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
    public  void should_get_all_todos() throws Exception {
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

    @AfterClass
    public static void tearDown() throws Exception {
        Spark.stop();
    }
}
