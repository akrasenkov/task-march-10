package edu.androidclub.noteless.config;

import edu.androidclub.noteless.data.NotesRepository;
import edu.androidclub.noteless.data.UsersRepository;
import edu.androidclub.noteless.data.datastore.DatastoreNotesRepository;
import edu.androidclub.noteless.data.datastore.DatastoreUsersRepository;
import org.glassfish.hk2.utilities.binding.AbstractBinder;


public class StorageBinding extends AbstractBinder {
    @Override
    protected void configure() {
        bind(DatastoreUsersRepository.class).to(UsersRepository.class);
        bind(DatastoreNotesRepository.class).to(NotesRepository.class);
    }

}
