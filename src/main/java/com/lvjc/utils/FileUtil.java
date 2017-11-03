package com.lvjc.utils;

import java.io.*;

/**
 * 文件流工具
 * Created by lvjc on 2017/11/2.
 */
public class FileUtil {

    /**
     * 读取文件内容
     * @param fileName
     * @return
     */
    public static String readFile(String fileName){
        File file = new File(fileName);
        StringBuilder builder = new StringBuilder();
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)))){
            String line;
            while ((line = reader.readLine()) != null){
                builder.append(line);
                builder.append("\r\n");
            }
        } catch (IOException e) {
            return null;
        }
        return builder.toString();
    }

    /**
     * 写入文件内容
     * @param content 待写入的内容
     * @param fileName 目标文件
     * @param append 是否从末尾开始写入
     * @throws IOException
     */
    public static void writeFile(String content, String fileName, boolean append) throws IOException {
        if(content == null)
            content = "";
        try(BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName, append)))){
            writer.write(content);
        }
    }

    /**
     * 复制文件内容
     * @param sourceFile 源文件
     * @param targetFile 目标文件
     * @throws IOException
     */
    public static void copyFile(String sourceFile, String targetFile) throws IOException {
        String content = readFile(sourceFile);
        if(content != null){
            try(BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(targetFile)))){
                writer.write(content);
            }
        }
    }
}
