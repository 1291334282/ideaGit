package com.graduation.handler;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 * @Author: Manitozhang
 * @Data: 2019/1/9 16:51
 * @Email: manitozhang@foxmail.com
 *
 * 文件工具类
 */
public class FileUtil {
//    @Value("${prop.upload-folder}")
//    private static String filePath;
    public static String getUploadPath(){
//        File path = null;
//        try {
//            path = new File(ResourceUtils.getURL("classpath:").getPath());
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//        if (!path.exists()) path = new File("");
//        File upload = new File(path.getAbsolutePath(), "static/img/");
//        if (!upload.exists()) upload.mkdirs();
//        System.out.println(upload.getAbsolutePath());
//        return upload.getAbsolutePath();
        String path="C:\\img";
        return path;
    }

}