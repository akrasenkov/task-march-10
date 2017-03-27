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
 * Created by senik11 on 23.03.17.
 */
public class AuthFilter implements ContainerRequestFilter {

    private final UsersRepository database;

    public AuthFilter(UsersRepository database) {
        this.database = database;
    }

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        final String authHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
        User user = database.getUserByToken(authHeader);
        if (user == null) {
            throw new ForbiddenException("invalid user");
        }
    }

}
