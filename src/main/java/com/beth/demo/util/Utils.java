package com.beth.demo.util;

import org.springframework.util.ResourceUtils;

import java.io.*;
import java.util.Properties;

public class Utils {

    public static Properties fetchProperties() {
        Properties properties = new Properties();
        try {
            File file = ResourceUtils.getFile("classpath:application.properties");
            InputStream in = new FileInputStream(file);
            properties.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }

    public static File getResourceFile(String filePath) throws Exception {
        File file = ResourceUtils.getFile(filePath);
        return file;
    }
}

