package edu.androidclub.noteless;

import edu.androidclub.noteless.data.UsersRepository;
import edu.androidclub.noteless.domain.User;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/users")
public class UsersResource {

    private final UsersRepository database;

    public UsersResource(UsersRepository database) {
        this.database = database;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public User getUserByToken(
            @QueryParam("token") String token
    ) {
        return database.getUserByToken(token);
    }

    @NoAuth
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public User createUser(
            @QueryParam("token") String token
    ) {
        return database.createUser(token, System.currentTimeMillis());
    }

}
