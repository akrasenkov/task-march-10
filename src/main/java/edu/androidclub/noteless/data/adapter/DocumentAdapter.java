package edu.androidclub.noteless.data.adapter;

import org.bson.Document;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

public class DocumentAdapter {

    public static <T> T fromDocument(Class<T> entityClass, Document document) {
        Object entity = null;
        try {
            entity = entityClass.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        Field[] classFields = entityClass.getDeclaredFields();
        for (Field classField : classFields) {
            String fieldName = classField.getName();
            Class<?> fieldClass = classField.getType();
            if (document.containsKey(fieldName)) {
                Object fieldValue = document.get(fieldName);
                boolean accessible = classField.isAccessible();
                if (!accessible) {
                    classField.setAccessible(true);
                }
                try {
                    classField.set(entity, fieldValue);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException("Can not convert document to entity!", e);
                }
                if (!accessible) {
                    classField.setAccessible(false);
                }
            } else {
                throw new RuntimeException("Field not found: " + classField);
            }
        }
        return entityClass.cast(entity);
    }

    public static <T> Document toDocument(T entity) {
        Document document = new Document();
        Class<?> entityClass = entity.getClass();
        Field[] classFields = entityClass.getDeclaredFields();
        for (Field classField : classFields) {
            String fieldName = classField.getName();
            boolean accessible = classField.isAccessible();
            if (!accessible) {
                classField.setAccessible(true);
            }
            try {
                Object fieldValue = classField.get(entity);
                document.put(fieldName, fieldValue);
            } catch (IllegalAccessException e) {
                throw new RuntimeException("Can not access field: " + classField);
            }
            if (!accessible) {
                classField.setAccessible(false);
            }
        }
        return document;
    }

}
