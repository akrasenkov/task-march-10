package edu.androidclub.noteless;

import com.googlecode.objectify.Key;

import java.security.Principal;

public class Bracelet implements Principal {

    private final Key<?> userKey;

    public Bracelet(Key<?> userKey) {
        this.userKey = userKey;
    }

    public Key<?> getUserKey() {
        return userKey;
    }

    @Override
    public String getName() {
        return getUserKey().toWebSafeString();
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Bracelet{");
        sb.append("userKey=").append(userKey);
        sb.append('}');
        return sb.toString();
    }
}
