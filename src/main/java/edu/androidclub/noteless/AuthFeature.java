package edu.androidclub.noteless;

import javax.inject.Inject;
import javax.ws.rs.container.DynamicFeature;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.FeatureContext;

public class AuthFeature implements DynamicFeature {

    private final Doorman filter;

    @Inject
    public AuthFeature(Doorman filter) {
        this.filter = filter;
    }

    @Override
    public void configure(
            ResourceInfo resourceInfo, // информация о смапленном ресурсе (классе и методе)
            FeatureContext context // функциональный контекст запроса
    ) {
        // проверяем, имеет ли выбранный МЕТОД аннотацию @NoAuth
        if (!resourceInfo.getResourceMethod().isAnnotationPresent(NoAuth.class)) {
            // если нет - применяем фильтр авторизации
            System.out.println("REGISTER: " + resourceInfo.getResourceMethod().getName());
            context.register(filter);
        }
    }
}
