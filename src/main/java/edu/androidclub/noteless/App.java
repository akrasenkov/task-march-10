package edu.androidclub.noteless;

import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import edu.androidclub.noteless.data.local.NotesMemoryStorage;

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
        this.singletons.add(new NotesResource(new NotesMemoryStorage()));
        this.classes.add(JacksonJaxbJsonProvider.class);
    }

    @Override
    public Set<Class<?>> getClasses() {
        return classes;
    }

    @Override
    public Set<Object> getSingletons() {
        return singletons;
    }
}
