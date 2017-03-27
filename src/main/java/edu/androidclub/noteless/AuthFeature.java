package edu.androidclub.noteless;

import javax.ws.rs.container.DynamicFeature;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.FeatureContext;

/**
 * Dynamic Feature.
 * Подключает переданный в конструктор фильтр, в зависимости от указанных условий.
 */
public class AuthFeature implements DynamicFeature {

    private final AuthFilter filter;

    public AuthFeature(AuthFilter filter) {
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
            context.register(filter);
        }
    }
}
