package edu.androidclub.noteless;

import edu.androidclub.noteless.data.UsersRepository;
import edu.androidclub.noteless.domain.User;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/users")
public class UsersResource {

    @Inject
    private UsersRepository database;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public User getUserByToken(
            @QueryParam("token") String token
    ) {
        return database.getUserByToken(token);
    }

    /**
     * Метод создания нового пользователя.
     * Мы не можем создать пользователя, если не можем получить доступ к этому методу БЕЗ токена авторизации,
     * поэтому помечаем этот метод аннотацией @NoAuth - {@link AuthFeature} не будет применять фильтр
     * авторизации к этому методу.
     * */
    @NoAuth
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public User createUser(
            @QueryParam("token") String token
    ) {
        return database.createUser(token, System.currentTimeMillis());
    }

}
