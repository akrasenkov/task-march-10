package edu.androidclub.noteless.data.datastore;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.Result;
import edu.androidclub.noteless.data.NotesRepository;
import edu.androidclub.noteless.domain.Note;

import static com.googlecode.objectify.ObjectifyService.ofy;

public class DatastoreNotesRepository implements NotesRepository {
    @Override
    public Iterable<Note> getNotes(Key<?> userKey) {
        return ofy().load().type(Note.class).ancestor(userKey).list();
    }

    @Override
    public String createNote(Key<?> userKey, String text, long timestamp) {
        Note note = new Note(text, timestamp);
        note.setAuthor(userKey);
        Result<Key<Note>> result = ofy().save().entity(note);
        return result.now().toWebSafeString();
    }

    @Override
    public Note getNote(String keyStr) throws NoteNotFoundException {
        Note note = (Note) ofy().load().key(Key.create(keyStr)).now();
        if (note == null) {
            throw new NoteNotFoundException();
        }
        return note;
    }

    @Override
    public void deleteNote(String id) throws NoteNotFoundException {

    }

    @Override
    public Note updateNote(String id, String text, long timestamp) throws NoteNotFoundException {
        return null;
    }
}
