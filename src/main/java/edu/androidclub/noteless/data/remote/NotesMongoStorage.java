package edu.androidclub.noteless.data.remote;

import com.mongodb.Function;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import edu.androidclub.noteless.data.NotesRepository;
import edu.androidclub.noteless.domain.Note;
import org.bson.Document;

import javax.inject.Inject;
import java.util.UUID;

import static com.mongodb.client.model.Filters.eq;

public class NotesMongoStorage implements NotesRepository {

    private static final String NOTES_COLLECTION = "notes";
    private final MongoCollection<Document> notesCollection;

    @Inject
    public NotesMongoStorage(MongoDatabase mongoDatabase) {
        this.notesCollection = mongoDatabase.getCollection(NOTES_COLLECTION);
    }

    @Override
    public Iterable<Note> getNotes() {
        return notesCollection.find().map(new Function<Document, Note>() {
            @Override
            public Note apply(Document document) {
                if (document == null) {
                    return null;
                }
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
        Document document = new Document();
        String id = UUID.randomUUID().toString();
        document.append("id", id).append("text", text).append("timestamp", timestamp);
        notesCollection.insertOne(document);
        return id;
    }

    @Override
    public Note getNote(String id) throws NoteNotFoundException {
        Document noteDocument = notesCollection.find(eq("id", id)).limit(1).first();
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

    }

    @Override
    public Note updateNote(String id, String text, long timestamp) throws NoteNotFoundException {
        return null;
    }
}

