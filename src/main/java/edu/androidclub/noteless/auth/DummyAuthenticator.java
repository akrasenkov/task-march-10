package edu.androidclub.noteless.auth;

import java.util.Map;

/**
 * Created by senik11 on 16.03.17.
 */
public class DummyAuthenticator implements Authenticator {

    private final Map<String, Object> usersDatabase;

    public DummyAuthenticator(Map<String, Object> usersDatabase) {
        this.usersDatabase = usersDatabase;
    }

    @Override
    public boolean authenticate(String token) {
        usersDatabase.put(token, null);
        System.err.println("token value = " + token);
        return true;
    }
}
