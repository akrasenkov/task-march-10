package edu.androidclub.noteless;

import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import edu.androidclub.noteless.data.local.NotesMemoryStorage;
import edu.androidclub.noteless.data.remote.NotesMongoStorage;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

@ApplicationPath("/")
public class App extends Application {

    private final Set<Class<?>> classes;
    private final Set<Object> singletons;

    private final MongoDatabase notesDatabase;

    public App() {
        this.classes = new HashSet<>();
        this.singletons = new HashSet<>();
        this.classes.add(JacksonJaxbJsonProvider.class);

        MongoClient mongoClient = new MongoClient(DbConfig.DB_HOST, DbConfig.DB_PORT);
        this.notesDatabase = mongoClient.getDatabase(DbConfig.DB_NAME_NOTES);

        this.singletons.add(
                new NotesResource(
                        new NotesMongoStorage(
                                notesDatabase.getCollection(DbConfig.DB_COLLECTION_NOTES)
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
