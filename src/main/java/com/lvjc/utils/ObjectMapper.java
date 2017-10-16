package com.lvjc.utils;

import java.lang.reflect.Field;

import static com.lvjc.utils.StringUtil.underlineToHump;

/**
 * Created by lvjc on 2017/10/12.
 */
public class ObjectMapper {

    public static <T, S> T map(S source, Class<T> targetClass) {
        if (source == null)
            return null;
        T targetObject;
        try {
            targetObject = targetClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            return null;
        }
        Field[] targetFields = targetClass.getDeclaredFields();
        for(Field field : targetFields){
            String fieldName = field.getName();
            fieldName = underlineToHump(fieldName);
            try {
                Field sourceField = source.getClass().getDeclaredField(fieldName);
                sourceField.setAccessible(true);
                Object value = sourceField.get(source);
                field.setAccessible(true);
                field.set(targetObject, value);
            } catch (IllegalAccessException | NoSuchFieldException e) {
                e.printStackTrace();
            }
        }
        return targetObject;
    }
}
