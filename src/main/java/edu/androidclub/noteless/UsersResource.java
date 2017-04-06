package edu.androidclub.noteless;

import edu.androidclub.noteless.auth.AuthDynamicFeature;
import edu.androidclub.noteless.auth.annotation.NoAuth;
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

    /**
     * Метод создания нового пользователя.
     * Мы не можем создать пользователя, если не можем получить доступ к этому методу БЕЗ токена авторизации,
     * поэтому помечаем этот метод аннотацией @NoAuth - {@link AuthDynamicFeature} не будет применять фильтр
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
