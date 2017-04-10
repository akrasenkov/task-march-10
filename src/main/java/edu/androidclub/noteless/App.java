package edu.androidclub.noteless;

import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import edu.androidclub.noteless.config.ComponentBinder;
import edu.androidclub.noteless.config.StorageBinding;
import edu.androidclub.noteless.data.NotesRepository;
import edu.androidclub.noteless.data.UsersRepository;
import edu.androidclub.noteless.data.local.NotesMemoryStorage;
import edu.androidclub.noteless.data.local.UsersMemoryStorage;
import edu.androidclub.noteless.data.remote.NotesMongoStorage;
import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

@ApplicationPath("/")
public class App extends ResourceConfig {

    public App() {
        register(new StorageBinding());
        register(new ComponentBinder());

        register(JacksonJaxbJsonProvider.class);
        register(UsersResource.class);
        register(NotesResource.class);
        register(AuthFeature.class);
    }

    public static class DbConfig {
        public static final String DB_HOST = "localhost";
        public static final int DB_PORT = 27000;
        public static final String DB_NAME_NOTES = "notes_db";
        public static final String DB_COLLECTION_NOTES = "notes";
    }
}
