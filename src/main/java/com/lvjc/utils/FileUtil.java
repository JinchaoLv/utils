package com.lvjc.utils;

import java.io.*;

/**
 * Created by lvjc on 2017/11/2.
 */
public class FileUtil {

    public static String readFile(String fileName){
        File file = new File(fileName);
        StringBuilder builder = new StringBuilder();
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)))){
            String line;
            while ((line = reader.readLine()) != null){
                builder.append(line);
            }
        } catch (IOException e) {
            return null;
        }
        return builder.toString();
    }

    public static void writeFile(String content, String fileName) throws IOException {
        if(content == null)
            content = "";
        try(BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName)))){
            writer.write(content);
        }
    }

    public static void copyFile(String sourceFile, String targetFile) throws IOException {
        String content = readFile(sourceFile);
        if(content != null){
            try(BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(targetFile)))){
                writer.write(content);
            }
        }
    }
}
