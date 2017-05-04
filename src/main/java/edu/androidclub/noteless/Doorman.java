package edu.androidclub.noteless;

import edu.androidclub.noteless.data.UsersRepository;
import edu.androidclub.noteless.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.ForbiddenException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.SecurityContext;
import java.io.IOException;
import java.security.Principal;

public class Doorman implements ContainerRequestFilter {

    private final Logger log = LoggerFactory.getLogger(Doorman.class);

    private final UsersRepository database;

    @Inject
    public Doorman(UsersRepository database) {
        this.database = database;
    }

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        System.out.println("FILTERED: " + requestContext);
        // Получаем HTTP заголовок запроса Authorizarion
        final String authHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
        log.debug("auth header: " + authHeader);
        if (authHeader == null) {
            log.debug("Auth header is null");
            throw new ForbiddenException("no token");
        }

        final String token = authHeader.substring(authHeader.lastIndexOf(' ') + 1);
        log.debug("auth token: " + token);
        // Проверяем, есть ли пользователь с таким токеном в базе данных
        final User user = database.getUserByToken(token);
        if (user == null) {
            // Нет пользователя - прекраащаем запрос со статусом 403
            log.debug("user is null");
            throw new ForbiddenException("invalid user");
        }

        requestContext.setSecurityContext(new SecurityContext() {
            @Override
            public Principal getUserPrincipal() {
                return new Bracelet(user.getKey());
            }

            @Override
            public boolean isUserInRole(String role) {
                return false;
            }

            @Override
            public boolean isSecure() {
                return false;
            }

            @Override
            public String getAuthenticationScheme() {
                return null;
            }
        });
    }

}
