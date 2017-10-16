package com.lvjc.utils;

import java.lang.reflect.Field;
import java.util.*;

/**
 * Created by lvjc on 2017/10/13.
 */
public class FieldUtil {

    /**
     * 按属性名称字典排序
     * @param fields
     * @return
     */
    public static Field[] dictionarySort(Field[] fields){
        Field[] newFields = new Field[fields.length];
        if(fields.length == 0)
            return newFields;
        FieldNode head = new FieldNode(fields[0]);
        for(int i = 1; i < fields.length; i++){
            FieldNode preNode = null;
            FieldNode compareNode = head;
            FieldNode thisNode = new FieldNode(fields[i]);
            while(compareNode != null && thisNode.compareTo(compareNode) > 0){
                preNode = compareNode;
                compareNode = compareNode.next;
            }
            if(preNode == null) {
                thisNode.next = head;
                head = thisNode;
            }
            else{
                thisNode.next = compareNode;
                preNode.next = thisNode;
            }
        }
        for(int i = 0; i < newFields.length; i++){
            newFields[i] = head.data;
            head = head.next;
        }
        return newFields;
    }

    /**
     * 获取所有属性，包含继承自父类的属性，同名属性子类覆盖父类
     * @param cla
     * @return
     */
    public static Field[] getDeclaredFieldsIncludingInherited(Class<?> cla){
        Field[] superFields = null;
        if(cla.getSuperclass() != null){
            superFields = getDeclaredFieldsIncludingInherited(cla.getSuperclass());
        }
        Field[] fields = cla.getDeclaredFields();
        if(superFields == null)
            return fields;
        else {
            Map<String, Field> fieldMap = new HashMap<>();
            for(Field field : superFields)
                fieldMap.put(field.getName(), field);
            for(Field field : fields)
                fieldMap.put(field.getName(), field);
            Field[] fieldsWithoutRepeated = new Field[fieldMap.size()];
            return fieldMap.values().toArray(fieldsWithoutRepeated);
        }
    }

    /**
     * 获取所有属性，包含继承自父类的属性，同名属性子类覆盖父类
     * @param cla
     * @return
     */
    public static Map<String, Field> getDeclaredFieldMapIncludingInherited(Class<?> cla){
        Field[] superFields = null;
        if(cla.getSuperclass() != null){
            superFields = getDeclaredFieldsIncludingInherited(cla.getSuperclass());
        }
        Field[] fields = cla.getDeclaredFields();
        Map<String, Field> fieldMap = new HashMap<>();
        for(Field field : superFields)
            fieldMap.put(field.getName(), field);
        for(Field field : fields)
            fieldMap.put(field.getName(), field);
        return fieldMap;
    }

    private static class FieldNode{
        private FieldNode next;
        private Field data;

        public FieldNode(Field field){
            this.data = field;
        }

        public int compareTo(FieldNode fieldNode){
            return this.data.getName().compareTo(fieldNode.data.getName());
        }
    }
}
