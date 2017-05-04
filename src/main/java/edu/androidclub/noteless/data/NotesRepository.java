package edu.androidclub.noteless.data;

import com.googlecode.objectify.Key;
import edu.androidclub.noteless.domain.Note;

public interface NotesRepository {

    Iterable<Note> getNotes(Key<?> userKey);

    String createNote(Key<?> userKey, String text, long timestamp);

    Note getNote(String id) throws NoteNotFoundException;

    void deleteNote(String id) throws NoteNotFoundException;

    Note updateNote(String id, String text, long timestamp) throws NoteNotFoundException;

    class NoteNotFoundException extends Exception {}
}
