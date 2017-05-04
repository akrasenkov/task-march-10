package edu.androidclub.noteless.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Parent;

@Entity(name = "Notes")
public class Note {

    @Id
    private Long id;

    @JsonIgnore
    @Parent
    private Key<?> author;

    @JsonProperty("text")
    private String text;

    private long timestamp;

    public Note() {
    }

    public Note(String text, long timestamp) {
        this.text = text;
        this.timestamp = timestamp;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @JsonIgnore
    public Key<?> getAuthor() {
        return author;
    }

    public void setAuthor(Key<?> author) {
        this.author = author;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Note note = (Note) o;

        if (timestamp != note.timestamp) return false;
        if (id != null ? !id.equals(note.id) : note.id != null) return false;
        if (author != null ? !author.equals(note.author) : note.author != null) return false;
        return text != null ? text.equals(note.text) : note.text == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (author != null ? author.hashCode() : 0);
        result = 31 * result + (text != null ? text.hashCode() : 0);
        result = 31 * result + (int) (timestamp ^ (timestamp >>> 32));
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Note{");
        sb.append("id=").append(id);
        sb.append(", author=").append(author);
        sb.append(", text='").append(text).append('\'');
        sb.append(", timestamp=").append(timestamp);
        sb.append('}');
        return sb.toString();
    }
}
