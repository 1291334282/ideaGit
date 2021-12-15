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
 * <p>
 * 文件工具类
 */
public class FileUtil {
    public static String getUploadPath() {
        String path = "C:\\img";//本地用
//        String path="/root/img";//阿里云用
        System.out.println(path);
        return path;
    }

}