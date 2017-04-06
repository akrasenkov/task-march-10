package edu.androidclub.noteless.auth;

import edu.androidclub.noteless.auth.annotation.NoAuth;

import javax.ws.rs.container.DynamicFeature;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.FeatureContext;
import java.lang.reflect.Method;

public class AuthDynamicFeature implements DynamicFeature {

    private final AuthRequestFilter authFilter;

    public AuthDynamicFeature(AuthRequestFilter authFilter) {
        this.authFilter = authFilter;
    }

    @Override
    public void configure(ResourceInfo resourceInfo, FeatureContext context) {
        Method resourceMethod = resourceInfo.getResourceMethod();
        if (!resourceMethod.isAnnotationPresent(NoAuth.class)) {
            context.register(authFilter);
        }
    }
}
