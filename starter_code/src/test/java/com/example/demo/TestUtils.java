package com.example.demo;

import java.lang.reflect.Field;

// this class was created following along Sareeta's video
public class TestUtils {
    public static void injectObjects(Object target, String fieldName, Object toInject){
        boolean wasPrivate = false;

        //through reflection
        try {
            Field f = target.getClass().getDeclaredField(fieldName);
            if(!f.isAccessible()){
                f.setAccessible(true);
                wasPrivate  = true;
            }
            f.set(target, toInject);

            if(wasPrivate){
                f.setAccessible(false);
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }
}