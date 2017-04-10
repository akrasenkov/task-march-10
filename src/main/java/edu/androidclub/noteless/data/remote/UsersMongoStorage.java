package edu.androidclub.noteless.data.remote;

import com.mongodb.Function;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import edu.androidclub.noteless.data.UsersRepository;
import edu.androidclub.noteless.data.adapter.DocumentAdapter;
import edu.androidclub.noteless.domain.User;
import org.bson.Document;

import javax.inject.Inject;
import javax.jws.soap.SOAPBinding;
import java.util.UUID;

import static com.mongodb.client.model.Filters.eq;

public class UsersMongoStorage implements UsersRepository {

    private static final String USERS_COLLECTION = "users";
    private MongoCollection<Document> usersCollection;

    @Inject
    public UsersMongoStorage(MongoDatabase mongoDatabase) {
        this.usersCollection = mongoDatabase.getCollection(USERS_COLLECTION);
    }

    @Override
    public User getUserByToken(String token) {
        return usersCollection.find(eq("token", token))
                .map(new Function<Document, User>() {
                    @Override
                    public User apply(Document document) {
                        if (document == null) {
                            return null;
                        }
                        return DocumentAdapter.fromDocument(User.class, document);
                    }
                })
                .first();
    }

    @Override
    public User createUser(String token, long timestamp) {
        User user = new User(UUID.randomUUID().toString(), token, timestamp);
        Document userDocument = DocumentAdapter.toDocument(user);
        usersCollection.insertOne(userDocument);
        return user;
    }
}
