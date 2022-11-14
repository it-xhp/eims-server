package com.gdupt.util;

import lombok.extern.slf4j.Slf4j;

import java.io.*;

/**
 * @author xuhuaping
 * @date 2022/9/13
 * 文件操作工具类
 */
@Slf4j
public class FileUtils {

    private static final String fileSeparator = "/";

    /**
     * 复制文件夹
     * @param sourcePath    原文件夹绝对路径
     * @param newPath       新文件夹绝对路径
     * @return              是否复制成功
     */
    public static boolean copyDirectory(String sourcePath, String newPath) {
        File file = new File(sourcePath);
        String[] fileNames = file.list();
        if (fileNames == null) {
            return false;
        }

        if (!(new File(newPath)).exists()) {
            (new File(newPath)).mkdir();
        }

        boolean isSuccess = true;
        for (String fileName : fileNames) {
            String sourceFilePath = sourcePath + fileSeparator + fileName;
            File sourceFile = new File(sourceFilePath);
            if (sourceFile.isDirectory()) {
                isSuccess &= copyDirectory(sourceFilePath, newPath + fileSeparator + fileName);
            }
            if (sourceFile.isFile()) {
                isSuccess &= copyFile(sourceFilePath, newPath + fileSeparator + fileName);
            }
        }
        return isSuccess;
    }

    /**
     * 复制文件
     * @param sourcePath    原文件绝对路径
     * @param newPath       新文件绝对路径
     * @return              是否复制成功
     */
    public static boolean copyFile(String sourcePath, String newPath) {
        File sourceFile = new File(sourcePath);
        File targetFile = new File(newPath);
        try (FileInputStream in = new FileInputStream(sourceFile);
             FileOutputStream out = new FileOutputStream(targetFile)) {
            byte[] buffer = new byte[2 * 1024 * 1024];
            int readByte;
            while((readByte = in.read(buffer)) != -1) {
                out.write(buffer, 0, readByte);
            }
            return true;
        } catch (IOException e) {
            log.error(e.toString(), e);
        }

        return false;
    }

    /**
     * 删除文件
     * <p>如果删除对象为文件夹，则递归删除其目录下的所有文件和文件夹</p>
     * @param file      将要删除的文件
     * @return          是否删除成功
     */
    public static boolean deleteFile(File file) {
        if (file.isDirectory()) {
            String[] childrenFileNames = file.list();
            if (childrenFileNames != null) {
                // 递归删除目录中的文件/文件夹
                for (String childFileName : childrenFileNames) {
                    if (!deleteFile(new File(file, childFileName))) {
                        return false;
                    }
                }
            }
        }
        // 为文件或空目录，可以删除
        return file.delete();
    }

    /**
     * 删除文件
     * <p>如果删除对象为文件夹，则递归删除其目录下的所有文件和文件夹</p>
     * @param filePath      将要删除的文件的绝对路径
     * @return              是否删除成功
     */
    public static boolean deleteFile(String filePath) {
        return deleteFile(new File(filePath));
    }

    /**
     * 在文件末尾添加内容
     * @param filePath  文件绝对路径
     * @param content   添加的内容
     * @return          是否操作成功
     */
    public static boolean appendString(String filePath, String content) {
        File file = new File(filePath);
        if (!file.exists()) {
            try {
                file.createNewFile();
                file = new File(filePath);
            } catch (IOException e) {
                log.error(e.toString(), e);
                return false;
            }
        }

        try (FileWriter fileWriter = new FileWriter(file, true)) {
            fileWriter.write(content);
        } catch (IOException e) {
            log.error(e.toString(), e);
            return false;
        }

        return true;
    }
}


