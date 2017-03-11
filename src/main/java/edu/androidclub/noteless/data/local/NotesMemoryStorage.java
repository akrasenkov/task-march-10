package edu.androidclub.noteless.data.local;

import edu.androidclub.noteless.data.NotesRepository;
import edu.androidclub.noteless.domain.Note;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class NotesMemoryStorage implements NotesRepository {

    private final ConcurrentHashMap<String, Note> storage;

    public NotesMemoryStorage() {
        this.storage = new ConcurrentHashMap<>();
    }

    @Override
    public Iterable<Note> getNotes() {
        return storage.values();
    }

    @Override
    public String createNote(String text, long timestamp) {
        String id = UUID.randomUUID().toString();
        Note note = new Note(id, text, timestamp);
        storage.put(id, note);
        return id;
    }

    @Override
    public Note getNote(String id) throws NoteNotFoundException {
        Note note = storage.get(id);
        if (note == null) {
            throw new NoteNotFoundException();
        }
        return note;
    }

    @Override
    public void deleteNote(String id) throws NoteNotFoundException {
        if (!storage.containsKey(id)) {
            throw new NoteNotFoundException();
        }
        storage.remove(id);
    }

    @Override
    public Note updateNote(String id, String text, long timestamp) throws NoteNotFoundException {
        if (!storage.containsKey(id)) {
            throw new NoteNotFoundException();
        }
        Note updatedNote = new Note(id, text, timestamp);
        storage.put(id, updatedNote);
        return updatedNote;
    }
}
