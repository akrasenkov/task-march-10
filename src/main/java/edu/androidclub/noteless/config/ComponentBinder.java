package edu.androidclub.noteless.config;

import edu.androidclub.noteless.AuthFilter;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

/**
 * Created by senik11 on 06.04.17.
 */
public class ComponentBinder extends AbstractBinder {
    @Override
    protected void configure() {
        bind(AuthFilter.class).to(AuthFilter.class);
    }
}
