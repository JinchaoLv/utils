package com.lvjc.utils;

/**
 * Created by lvjc on 2017/10/13.
 */
public class ClassUtil {

    /**
     * 获取简单类名，并将首字母小写
     * @param c
     * @return
     */
    public static String defaultObjectName(Class<?> c){
        String fullClassName = c.toString();
        int lastDot = fullClassName.lastIndexOf(".");
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append((char)(fullClassName.charAt(lastDot + 1) + 32));
        stringBuilder.append(fullClassName.substring(lastDot + 2));
        return stringBuilder.toString();
    }
}
