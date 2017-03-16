package edu.androidclub.noteless.auth;

/**
 * Created by senik11 on 16.03.17.
 */
public interface Authenticator {

    boolean authenticate(String token);

}
