package fr.xebia.xke.dagger;

import com.jayway.restassured.RestAssured;
import dagger.ObjectGraph;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import spark.Spark;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;

public class SparkServerTest {

    @Before
    public void setUp() throws Exception {
        ObjectGraph objectGraph = ObjectGraph.create(new TodosInMemoryModule());
        objectGraph.get(SparkServer.class).launch();
    }

    @Test
    public void should_get_all_todos() throws Exception {
        RestAssured.given().port(4567)
                .when().get("/todos")
                .then().assertThat().body("_id", hasSize(3));
    }

    @After
    public void tearDown() throws Exception {
        Spark.stop();
    }
}
