package edu.androidclub.noteless.domain;

/**
 * Created by senik11 on 23.03.17.
 */
public class User {

    private final String id;

    private final String token;

    private final long timestamp;

    public User(String id, String token, long timestamp) {
        this.id = id;
        this.token = token;
        this.timestamp = timestamp;
    }

    public String getId() {
        return id;
    }

    public String getToken() {
        return token;
    }

    public long getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", token='" + token + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}
