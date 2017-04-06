package edu.androidclub.noteless.data;

import edu.androidclub.noteless.domain.User;

public interface UsersRepository {

    User getUserByToken(String token);

    User createUser(String token, long timestamp);

}