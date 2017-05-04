package edu.androidclub.noteless.config;

import edu.androidclub.noteless.AuthData;
import edu.androidclub.noteless.AuthDataValueFactoryProvider;
import edu.androidclub.noteless.Doorman;
import org.glassfish.hk2.api.InjectionResolver;
import org.glassfish.hk2.api.TypeLiteral;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.spi.internal.ValueFactoryProvider;

import javax.inject.Singleton;


public class ComponentBinder extends AbstractBinder {
    @Override
    protected void configure() {
        bind(Doorman.class).to(Doorman.class);
        bind(AuthDataValueFactoryProvider.class).to(ValueFactoryProvider.class).in(Singleton.class);
        bind(AuthDataValueFactoryProvider.InjectionResolver.class).to(new TypeLiteral<InjectionResolver<AuthData>>() {}).in(Singleton.class);
    }
}
