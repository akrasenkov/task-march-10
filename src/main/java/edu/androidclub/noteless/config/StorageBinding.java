package edu.androidclub.noteless.config;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import edu.androidclub.noteless.App;
import edu.androidclub.noteless.data.NotesRepository;
import edu.androidclub.noteless.data.UsersRepository;
import edu.androidclub.noteless.data.remote.NotesMongoStorage;
import edu.androidclub.noteless.data.remote.UsersMongoStorage;
import org.glassfish.hk2.api.Factory;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

import javax.inject.Singleton;

public class StorageBinding extends AbstractBinder {
    @Override
    protected void configure() {
        bind(UsersMongoStorage.class).to(UsersRepository.class);
        bind(NotesMongoStorage.class).to(NotesRepository.class);
        bindFactory(MongoDatabaseFactory.class).to(MongoDatabase.class).in(Singleton.class);
    }

    public static class MongoDatabaseFactory implements Factory<MongoDatabase> {

        @Override
        public MongoDatabase provide() {
            MongoClient mongoClient = new MongoClient(App.DbConfig.DB_HOST, App.DbConfig.DB_PORT);
            return mongoClient.getDatabase(App.DbConfig.DB_NAME_NOTES);
        }

        @Override
        public void dispose(MongoDatabase instance) {

        }
    }
}
