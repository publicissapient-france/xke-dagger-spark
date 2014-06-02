package fr.xebia.xke.dagger;

import com.fasterxml.jackson.databind.ObjectMapper;
import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;

@Module(
        injects = SparkServer.class,
        includes = TodosRepositoryModule.class
)
public class TodosModule {
    @Provides @Singleton ObjectMapper provideJacksonMapper() {
        return new ObjectMapper();
    }
}
