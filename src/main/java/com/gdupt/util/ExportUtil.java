package com.gdupt.util;

import org.apache.poi.util.IOUtils;
import org.springframework.core.io.ClassPathResource;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * @author xuhuaping
 * @date 2022/10/11
 */
public class ExportUtil {

    /**
     * 下载
     * @param response
     * @param fileName
     * @param filePath
     * @throws IOException
     */
    public static void download(HttpServletResponse response, String fileName, String filePath) throws IOException {

        File file = new File(filePath);
        FileInputStream in = new FileInputStream(file);
        try (OutputStream os = response.getOutputStream()) {
            fileName = String.format("%s", fileName);
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
            response.setHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1));
            response.addHeader("Cache-Control", "no-cache");
            byte[] bs = new byte[1024];
            int count = 0;

            while ((count=in.read(bs, 0, bs.length))!=-1) {
                os.write(bs,0,count);
            }
            in.close();
            os.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 下载Excel模板
     * @param response
     * @param resourceFileName
     * @param resourceFilePath
     * @throws IOException
     */
    public static void downloadExcelTemplate(HttpServletResponse response, String resourceFileName, String resourceFilePath) throws IOException {
        ClassPathResource file = new ClassPathResource(resourceFilePath);
        InputStream inputStream = file.getInputStream();
        try (OutputStream os = response.getOutputStream()) {
            String fileName = String.format("%s", resourceFileName);
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
            response.setHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1));
            response.addHeader("Cache-Control", "no-cache");
            IOUtils.copy(inputStream, os);
            inputStream.close();
            os.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
