package edu.androidclub.noteless.data.local;

import com.mongodb.Function;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.FindOneAndUpdateOptions;
import com.mongodb.client.model.ReturnDocument;
import edu.androidclub.noteless.data.NotesRepository;
import edu.androidclub.noteless.domain.Note;
import org.bson.Document;

import java.util.UUID;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.combine;
import static com.mongodb.client.model.Updates.set;

/**
 * Created by senik11 on 16.03.17.
 */
public class NotesMongoStorage implements NotesRepository {

    private final static String NOTES_COLLECTION = "notes";

    private MongoCollection<Document> notes;

    public NotesMongoStorage(MongoDatabase mongoDb) {
        this.notes = mongoDb.getCollection(NOTES_COLLECTION);
    }

    @Override
    public Iterable<Note> getNotes() {
        return notes.find().map(new Function<Document, Note>() {
            @Override
            public Note apply(Document document) {
                return new Note(
                        document.getString("id"),
                        document.getString("text"),
                        document.getLong("timestamp")
                );
            }
        });
    }

    @Override
    public String createNote(String text, long timestamp) {
        Document note = new Document();
        String noteId = UUID.randomUUID().toString();
        note.append("id", noteId)
            .append("text", text)
            .append("timestamp", timestamp);
        notes.insertOne(note);
        return noteId;
    }

    @Override
    public Note getNote(String id) throws NoteNotFoundException {
        FindIterable<Document> matchedNotes = notes.find(eq("id", id)).limit(1);
        Document noteDocument = matchedNotes.first();
        if (noteDocument == null) {
            throw new NoteNotFoundException();
        }
        return new Note(
                noteDocument.getString("id"),
                noteDocument.getString("text"),
                noteDocument.getLong("timestamp")
        );
    }

    @Override
    public void deleteNote(String id) throws NoteNotFoundException {
        if (notes.findOneAndDelete(eq("id", id)) == null) {
            throw new NoteNotFoundException();
        }
    }

    @Override
    public Note updateNote(String id, String text, long timestamp) throws NoteNotFoundException {
        Document updatedNoteDocument = notes.findOneAndUpdate(
                eq("id", id),
                combine(
                        set("text", text),
                        set("timestamp", timestamp)
                ),
                new FindOneAndUpdateOptions().returnDocument(ReturnDocument.AFTER)
        );
        if (updatedNoteDocument == null) {
            throw new NoteNotFoundException();
        }
        return new Note(
                updatedNoteDocument.getString("id"),
                updatedNoteDocument.getString("text"),
                updatedNoteDocument.getLong("timestamp")
        );
    }
}
