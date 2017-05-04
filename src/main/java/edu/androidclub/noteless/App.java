package edu.androidclub.noteless;

import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import com.googlecode.objectify.ObjectifyService;
import edu.androidclub.noteless.config.ComponentBinder;
import edu.androidclub.noteless.config.StorageBinding;
import edu.androidclub.noteless.domain.Note;
import edu.androidclub.noteless.domain.User;
import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.ApplicationPath;

@ApplicationPath("/")
public class App extends ResourceConfig {

    public App() {
        ObjectifyService.register(User.class);
        ObjectifyService.register(Note.class);

        register(new StorageBinding());
        register(new ComponentBinder());

        register(JacksonJaxbJsonProvider.class);
        register(UsersResource.class);
        register(NotesResource.class);
        register(AuthFeature.class);
    }
}
