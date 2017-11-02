package com.lvjc.utils;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Date;
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

    public static void setProperties(Class<?> targetObjectClass, String targetObjectName, String paramName, String fileName){
        StringBuilder builder = new StringBuilder();
        String ifFormat = "if(" + paramName + ".containsKey(\"%s\"))";
        String stringFormat = targetObjectName + ".set%s((String) " + paramName + ".get(\"%s\"));";
        String intFormat = targetObjectName + ".set%s(this.getInteger(" + paramName + ".get(\"%s\")));";
        String booleanFormat = targetObjectName + ".set%s(this.getBoolean(" + paramName + ".get(\"%s\")));";
        String bigDecimalFormat = targetObjectName + ".set%s(this.getBigDecimal(" + paramName + ".get(\"%s\")));";
        String doubleFormat = targetObjectName + ".set%s(this.getDouble(" + paramName + ".get(\"%s\")));";
        String dateFormat = targetObjectName + ".set%s(this.getDate(" + paramName + ".get(\"%s\")));";

        Field[] fields = FieldUtil.getDeclaredFieldsIncludingInherited(targetObjectClass);
        Field[] sortedFields = FieldUtil.dictionarySort(fields);
        for(Field field : sortedFields){
            String fieldName = field.getName();
            Class fieldType = field.getType();

            builder.append(String.format(ifFormat, fieldName));
            builder.append("\r\n");
            builder.append("\t");
            if(fieldType == Integer.class)
                builder.append(String.format(intFormat, StringUtil.capitalizedFirstLetter(fieldName), fieldName));
            else if(fieldType == String.class)
                builder.append(String.format(stringFormat, StringUtil.capitalizedFirstLetter(fieldName), fieldName));
            else if(fieldType == Boolean.class)
                builder.append(String.format(booleanFormat, StringUtil.capitalizedFirstLetter(fieldName), fieldName));
            else if(fieldType == BigDecimal.class)
                builder.append(String.format(bigDecimalFormat, StringUtil.capitalizedFirstLetter(fieldName), fieldName));
            else if(fieldType == Double.class)
                builder.append(String.format(doubleFormat, StringUtil.capitalizedFirstLetter(fieldName), fieldName));
            else if(fieldType.equals(Date.class))
                builder.append(String.format(dateFormat, StringUtil.capitalizedFirstLetter(fieldName), fieldName));
            else
                builder.append("error:没有匹配的类型");
            builder.append("\r\n");
        }
        try(BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName)))){
            writer.write(builder.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
