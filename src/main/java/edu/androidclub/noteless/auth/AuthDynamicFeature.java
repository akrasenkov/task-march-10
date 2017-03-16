package edu.androidclub.noteless.auth;

import edu.androidclub.noteless.auth.annotation.Auth;

import javax.ws.rs.container.DynamicFeature;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.FeatureContext;
import java.lang.reflect.Method;

/**
 * Created by senik11 on 16.03.17.
 */
public class AuthDynamicFeature implements DynamicFeature {

    private final AuthRequestFilter authFilter;

    public AuthDynamicFeature(AuthRequestFilter authFilter) {
        this.authFilter = authFilter;
    }

    @Override
    public void configure(ResourceInfo resourceInfo, FeatureContext context) {
        Method resourceMethod = resourceInfo.getResourceMethod();
        if (resourceMethod.isAnnotationPresent(Auth.class)) {
            context.register(authFilter);
        }
    }
}
