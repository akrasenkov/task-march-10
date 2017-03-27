package edu.androidclub.noteless;

import javax.ws.rs.container.DynamicFeature;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.FeatureContext;

/**
 * Created by senik11 on 23.03.17.
 */
public class AuthFeature implements DynamicFeature {

    private final AuthFilter filter;

    public AuthFeature(AuthFilter filter) {
        this.filter = filter;
    }

    @Override
    public void configure(ResourceInfo resourceInfo, FeatureContext context) {
        if (!resourceInfo.getResourceMethod().isAnnotationPresent(NoAuth.class)) {
            context.register(filter);
        }
    }
}
