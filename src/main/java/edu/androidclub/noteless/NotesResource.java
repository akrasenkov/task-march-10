package edu.androidclub.noteless;

import edu.androidclub.noteless.data.NotesRepository;
import edu.androidclub.noteless.domain.Note;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;


@Path("/notes")
public class NotesResource {

    private final Logger log = LoggerFactory.getLogger(NotesResource.class);

    @Inject
    private NotesRepository notesRepository;

    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    public Response createNote(String rawNote, @AuthData Bracelet bracelet) {
        long timestamp = System.currentTimeMillis();
        String noteId = notesRepository.createNote(bracelet.getUserKey(), rawNote, timestamp);

        log.debug("CURRENT USER: " + bracelet);

        URI noteUri = buildNoteUri(noteId);
        return Response.created(noteUri).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Iterable<Note> getAllNotes(@AuthData Bracelet bracelet) {
        return notesRepository.getNotes(bracelet.getUserKey());
    }

    @GET
    @Path("/{noteId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Note getNote(@PathParam("noteId") String id) {
        try {
            return notesRepository.getNote(id);
        } catch (NotesRepository.NoteNotFoundException e) {
            throw new NotFoundException();
        }
    }

    @PUT
    @Path("/{noteId}")
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateNote(@PathParam("noteId") String id, String text) {
        try {
            long timestamp = System.currentTimeMillis();
            Note updatedNote = notesRepository.updateNote(id, text, timestamp);

            URI noteUri = buildNoteUri(id);
            return Response.ok().entity(updatedNote).location(noteUri).build();
        } catch (NotesRepository.NoteNotFoundException e) {
            throw new NotFoundException();
        }
    }

    @DELETE
    @Path("/{noteId}")
    public Response deleteNote(@PathParam("noteId") String id) {
        try {
            notesRepository.deleteNote(id);
        } catch (NotesRepository.NoteNotFoundException e) {
            throw new NotFoundException();
        }
        return Response.ok().build();
    }

    private URI buildNoteUri(String noteId) {
        return UriBuilder
                .fromResource(NotesResource.class)
                .path(noteId)
                .build();
    }

}
