package edu.androidclub.noteless;

import com.googlecode.objectify.Key;
import edu.androidclub.noteless.data.UsersRepository;
import edu.androidclub.noteless.domain.User;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

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
    public Response createUser(
            @QueryParam("token") String token
    ) {
        User user = database.createUser(token, System.currentTimeMillis());
        Key<?> userKey = Key.create(User.class, user.getId());
        return Response.created(
                UriBuilder.fromResource(UsersResource.class).path(userKey.toWebSafeString()).build()
        ).entity(user).build();
    }

}
