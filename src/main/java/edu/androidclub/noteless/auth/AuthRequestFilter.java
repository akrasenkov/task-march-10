package edu.androidclub.noteless.auth;

import javax.ws.rs.ForbiddenException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import java.io.IOException;

public class AuthRequestFilter implements ContainerRequestFilter {

    private final Authenticator authenticator;

    public AuthRequestFilter(Authenticator authenticator) {
        this.authenticator = authenticator;
    }

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        String authHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
        if (!authenticator.authenticate(authHeader)) {
            throw new ForbiddenException();
        }
    }

}
