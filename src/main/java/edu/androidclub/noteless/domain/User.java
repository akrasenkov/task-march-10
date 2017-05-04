package edu.androidclub.noteless.domain;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

@Entity(name = "Users")
public class User {

    @Id
    private Long id;

    @Index
    private String token;

    private long timestamp;

    public User() {
    }

    public User(String token, long timestamp) {
        this.token = token;
        this.timestamp = timestamp;
    }

    public Key<?> getKey() {
        if (id == null) {
            return null;
        }
        return Key.create(User.class, id);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
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


}
