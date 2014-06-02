package fr.xebia.xke.dagger;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import dagger.Module;
import dagger.Provides;
import fr.xebia.xke.dagger.repository.TodosMongoRepository;
import fr.xebia.xke.dagger.repository.TodosRepository;
import org.jongo.Jongo;
import org.jongo.MongoCollection;

import javax.inject.Singleton;
import java.net.UnknownHostException;
import java.util.Arrays;

import static com.mongodb.MongoCredential.createMongoCRCredential;
import static java.lang.System.getProperty;

@Module(library = true)
public class TodosRepositoryModule {


    @Provides
    TodosRepository provideTodosRepository(TodosMongoRepository mongoRepository) {
        return mongoRepository;
    }

    @Provides
    @Singleton
    MongoCollection provideTodosMongoCollection(Jongo jongo) {
        return jongo.getCollection("todos");
    }

    @Provides
    @Singleton
    Jongo provideJongo() {
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
