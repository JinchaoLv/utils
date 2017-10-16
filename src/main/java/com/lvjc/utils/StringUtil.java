package com.lvjc.utils;

/**
 * Created by lvjc on 2017/10/13.
 */
public class StringUtil {

    /**
     * 下划线转驼峰
     * @param str
     * @return
     */
    public static String underlineToHump(String str){
        StringBuilder builder = new StringBuilder();
        boolean needUpperCase = false;
        for(int i = 0; i < str.length(); i++){
            char ch = str.charAt(i);
            if(ch == '_'){
                needUpperCase = true;
                continue;
            }
            if(needUpperCase){
                builder.append((char) (ch - 32));
                needUpperCase = false;
            }else {
                builder.append(ch);
            }
        }
        return builder.toString();
    }

    /**
     * 如字符串以小写字母开头，则将首字母大写
     * @param str
     * @return
     */
    public static String capitalizedFirstLetter(String str){
        char firstLetter = str.charAt(0);
        if(firstLetter <= 'z' && firstLetter >= 'a')
            firstLetter = (char) (firstLetter - 32);
        return firstLetter + str.substring(1);
    }
}
