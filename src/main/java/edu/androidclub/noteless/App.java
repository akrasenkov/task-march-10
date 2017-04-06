package edu.androidclub.noteless;

import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import edu.androidclub.noteless.data.UsersRepository;
import edu.androidclub.noteless.data.local.NotesMemoryStorage;
import edu.androidclub.noteless.data.local.UsersMemoryStorage;
import edu.androidclub.noteless.data.remote.NotesMongoStorage;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

@ApplicationPath("/")
public class App extends Application {

    private final Set<Class<?>> classes;
    private final Set<Object> singletons;

    public App() {
        this.classes = new HashSet<>();
        this.singletons = new HashSet<>();
        this.classes.add(JacksonJaxbJsonProvider.class);

        UsersRepository usersRepository = new UsersMemoryStorage();

        this.singletons.add(
                new NotesResource(
                        new NotesMemoryStorage()
                )
        );
        this.singletons.add(
                new UsersResource(
                        usersRepository
                )
        );
        this.singletons.add(
                new AuthFeature(
                        new AuthFilter(
                                usersRepository
                        )
                )
        );
    }

    @Override
    public Set<Class<?>> getClasses() {
        return classes;
    }

    @Override
    public Set<Object> getSingletons() {
        return singletons;
    }

    public static class DbConfig {
        public static final String DB_HOST = "localhost";
        public static final int DB_PORT = 27018;
        public static final String DB_NAME_NOTES = "notes_db";
        public static final String DB_COLLECTION_NOTES = "notes";
    }
}
