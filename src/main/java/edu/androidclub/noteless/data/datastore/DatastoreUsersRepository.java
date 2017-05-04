package edu.androidclub.noteless.data.datastore;

import edu.androidclub.noteless.data.UsersRepository;
import edu.androidclub.noteless.domain.User;

import static com.googlecode.objectify.ObjectifyService.ofy;

/**
 * Created by senik11 on 20.04.17.
 */
public class DatastoreUsersRepository implements UsersRepository {
    @Override
    public User getUserByToken(String token) {
        return ofy().load().type(User.class).filter("token", token).first().now();
    }

    @Override
    public User createUser(String token, long timestamp) {
        User user = new User(token, timestamp);
        ofy().save().entity(user).now();
        return user;
    }
}
