package edu.androidclub.noteless;

import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import edu.androidclub.noteless.data.local.NotesMemoryStorage;
import edu.androidclub.noteless.data.local.NotesMongoStorage;

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
        this.singletons.add(new NotesResource(new NotesMongoStorage(connectDb())));
        this.classes.add(JacksonJaxbJsonProvider.class);
    }

    private MongoDatabase connectDb() {
        MongoClient client = new MongoClient(DbConfig.DB_HOST, DbConfig.DB_PORT);
        return client.getDatabase(DbConfig.NOTE_DB_NAME);
    }

    @Override
    public Set<Class<?>> getClasses() {
        return classes;
    }

    @Override
    public Set<Object> getSingletons() {
        return singletons;
    }

    private static class DbConfig {
        public static final String DB_HOST = "localhost";
        public static final int DB_PORT = 27017;
        public static final String NOTE_DB_NAME = "notes_db";
    }
}
