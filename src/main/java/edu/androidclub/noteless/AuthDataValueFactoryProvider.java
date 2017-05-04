package edu.androidclub.noteless;

import org.glassfish.hk2.api.Factory;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.jersey.server.internal.inject.AbstractContainerRequestValueFactory;
import org.glassfish.jersey.server.internal.inject.AbstractValueFactoryProvider;
import org.glassfish.jersey.server.internal.inject.MultivaluedParameterExtractorProvider;
import org.glassfish.jersey.server.internal.inject.ParamInjectionResolver;
import org.glassfish.jersey.server.model.Parameter;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.ForbiddenException;
import java.security.Principal;

public class AuthDataValueFactoryProvider extends AbstractValueFactoryProvider {

    @Inject
    protected AuthDataValueFactoryProvider(MultivaluedParameterExtractorProvider mpep, ServiceLocator locator) {
        super(mpep, locator, Parameter.Source.UNKNOWN);
    }

    @Override
    protected Factory<?> createValueFactory(Parameter parameter) {
        if (isNoAuthDataAnnotation(parameter) || isNotBraceletClass(parameter)) {
            return null;
        }
        return new AbstractContainerRequestValueFactory<Bracelet>() {
            @Override
            public Bracelet provide() {
                Principal bracelet = getContainerRequest().getSecurityContext().getUserPrincipal();
                if (bracelet == null) {
                    throw new ForbiddenException("Unauthenticated request");
                }
                return (Bracelet) bracelet;
            }
        };
    }

    private static boolean isNoAuthDataAnnotation(Parameter parameter) {
        return !parameter.isAnnotationPresent(AuthData.class);
    }

    private static boolean isNotBraceletClass(Parameter parameter) {
        return !parameter.getRawType().equals(Bracelet.class);
    }

    @Singleton
    public static class InjectionResolver extends ParamInjectionResolver<AuthData> {
        public InjectionResolver() {
            super(AuthDataValueFactoryProvider.class);
        }
    }
}
