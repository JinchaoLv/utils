package com.lvjc.utils;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.lang.reflect.Field;
import java.util.Map;

/**
 * Created by lvjc on 2017/10/12.
 */
public class JavaCodeGenerator {

    /**
     * 生成代码：从source类获取属性设置给target类
     * 从source类获取属性的优先级：1.属性名相同，2.属性名转为驼峰命名，3.不设置此条属性
     * @param source
     * @param target
     * @param fileName
     */
    public static void setProperties(Class<?> source,  Class<?> target, String fileName){
        StringBuilder builder = new StringBuilder();
        String format = "%s.set%s(%s.get%s())";
        String sourceObjectName = ClassUtil.defaultObjectName(source);
        String targetObjectName = ClassUtil.defaultObjectName(target);
        String targetFieldName;
        String sourceFieldName = null;

        builder.append(target.getSimpleName() + " " + targetObjectName + " = new " + target.getSimpleName() + "();\r\n");

        Field[] fields = FieldUtil.getDeclaredFieldsIncludingInherited(target);
        Field[] sortedFields = FieldUtil.dictionarySort(fields);

        Map<String, Field> sourceFieldsMap = FieldUtil.getDeclaredFieldMapIncludingInherited(source);

        for(Field field : sortedFields){
            targetFieldName = field.getName();
            if(sourceFieldsMap.containsKey(targetFieldName))
                sourceFieldName = targetFieldName;
            else{
                String hump = StringUtil.underlineToHump(targetFieldName);
                if(sourceFieldsMap.containsKey(hump))
                    sourceFieldName = hump;
            }
            if(sourceFieldName != null){
                builder.append(String.format(format, targetObjectName, StringUtil.capitalizedFirstLetter(targetFieldName),
                        sourceObjectName, StringUtil.capitalizedFirstLetter(sourceFieldName)));
                builder.append(";\r\n");
                sourceFieldName = null;
            } else {
                builder.append(targetFieldName + ": 找不到匹配的属性！\r\n");
            }
        }

        builder.append("return " + targetObjectName + ";");

        try(BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName)))){
            writer.write(builder.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
