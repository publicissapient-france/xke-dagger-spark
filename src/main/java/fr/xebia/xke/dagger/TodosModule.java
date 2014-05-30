package fr.xebia.xke.dagger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import dagger.Module;
import dagger.Provides;
import org.jongo.Jongo;
import org.jongo.MongoCollection;

import javax.inject.Singleton;
import java.net.UnknownHostException;
import java.util.Arrays;

import static com.mongodb.MongoCredential.createMongoCRCredential;
import static java.lang.System.getProperty;

@Module(
        injects = SparkServer.class
)
public class TodosModule {

    @Provides @Singleton ObjectMapper provideJacksonMapper() {
        return new ObjectMapper();
    }

    @Provides @Singleton MongoCollection provideTodosMongoCollection(Jongo jongo) {
        return jongo.getCollection("todos");
    }

    @Provides @Singleton Jongo provideJongo() {
        try {
            MongoCredential credential = createMongoCRCredential("xke", getProperty("daggerMongoUser"),
                    getProperty("daggerMongoPwd").toCharArray());
            MongoClient mongoClient = new MongoClient(new ServerAddress("ds057847.mongolab.com", 57847), Arrays.asList(credential));
            return new Jongo(mongoClient.getDB("xke"));
        } catch (UnknownHostException e) {
            throw new RuntimeException("Couldn't configure mongo client", e);
        }
    }
}
