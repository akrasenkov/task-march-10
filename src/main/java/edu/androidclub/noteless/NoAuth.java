package edu.androidclub.noteless;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Аннотация для пометки методов, не требующих авторизации.
 * */
@Target(ElementType.METHOD) // Только для методов
@Retention(RetentionPolicy.RUNTIME) // Доступна в рантайме
public @interface NoAuth {
}
