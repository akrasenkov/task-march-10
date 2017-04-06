package edu.androidclub.noteless.auth;

public interface Authenticator {

    boolean authenticate(String token);

}
