package com.lvjc.utils;

import javassist.*;
import javassist.bytecode.CodeAttribute;
import javassist.bytecode.LocalVariableAttribute;
import javassist.bytecode.MethodInfo;

import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by lvjc on 2017/10/16.
 */
public class ReflectUtil {

    /**
     * 获取方法参数名，及对应参数值
     * @param className
     * @param methodName
     * @param args
     * @return
     */
    public static Map<String, Object> getMethodParamNameAndValue(String className, String methodName, Object[] args) throws NotFoundException {
        ClassPool pool = ClassPool.getDefault();
        ClassClassPath classPath = new ClassClassPath(ReflectUtil.class);
        pool.insertClassPath(classPath);
        CtClass cc = pool.get(className);
        CtMethod cm = cc.getDeclaredMethod(methodName);
        MethodInfo methodInfo = cm.getMethodInfo();
        CodeAttribute codeAttribute = methodInfo.getCodeAttribute();
        LocalVariableAttribute attr = (LocalVariableAttribute) codeAttribute.getAttribute(LocalVariableAttribute.tag);
        int pos = Modifier.isStatic(cm.getModifiers()) ? 0 : 1;
        Map<String, Object> paramMap = new HashMap<>(args.length);
        for (int i = 0; i < args.length; i++){
            paramMap.put(attr.variableName(i + pos), args[i]);
        }
        return paramMap;
    }
}
