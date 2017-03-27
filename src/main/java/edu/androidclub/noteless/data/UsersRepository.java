package edu.androidclub.noteless.data;

import edu.androidclub.noteless.domain.User;

/**
 * Created by senik11 on 23.03.17.
 */
public interface UsersRepository {

    User getUserByToken(String token);

    User createUser(String token, long timestamp);

}
