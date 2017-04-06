package edu.androidclub.noteless.data.remote;

import com.mongodb.Function;
import com.mongodb.client.MongoCollection;
import edu.androidclub.noteless.data.UsersRepository;
import edu.androidclub.noteless.data.adapter.DocumentAdapter;
import edu.androidclub.noteless.domain.User;
import org.bson.Document;

import java.util.UUID;

import static com.mongodb.client.model.Filters.eq;

public class UsersMongoStorage implements UsersRepository {

    private MongoCollection<Document> usersCollection;

    public UsersMongoStorage(MongoCollection<Document> usersCollection) {
        this.usersCollection = usersCollection;
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
