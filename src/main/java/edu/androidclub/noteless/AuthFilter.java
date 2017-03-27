package edu.androidclub.noteless;

import edu.androidclub.noteless.data.UsersRepository;
import edu.androidclub.noteless.domain.User;

import javax.sql.rowset.FilteredRowSet;
import javax.ws.rs.ForbiddenException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import java.io.IOException;

/**
 * Динамически подключаемый фильтр авторизации.
 */
public class AuthFilter implements ContainerRequestFilter {

    private final UsersRepository database;

    public AuthFilter(UsersRepository database) {
        this.database = database;
    }

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        // Получаем HTTP заголовок запроса Authorizarion
        final String authHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);

        // Проверяем, есть ли пользователь с таким токеном в базе данных
        User user = database.getUserByToken(authHeader);
        if (user == null) {
            // Нет пользователя - прекраащаем запрос со статусом 403
            throw new ForbiddenException("invalid user");
        }
    }

}
