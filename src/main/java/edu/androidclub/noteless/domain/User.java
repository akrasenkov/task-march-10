package edu.androidclub.noteless.domain;

/**
 * Created by senik11 on 23.03.17.
 */
public class User {

    private String id;

    private String token;

    private long timestamp;

    public User() {
    }

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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (timestamp != user.timestamp) return false;
        if (id != null ? !id.equals(user.id) : user.id != null) return false;
        return token != null ? token.equals(user.token) : user.token == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (token != null ? token.hashCode() : 0);
        result = 31 * result + (int) (timestamp ^ (timestamp >>> 32));
        return result;
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
