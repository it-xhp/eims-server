package com.gdupt.util;


import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

/**
 * @author xuhuaping
 * @date 2022/9/14
 */
public class ExcelUtils {

    public static final String EXCEL_2003 =".xls";

    public static final String EXCEL_2007 =".xlsx";
    /**
     * 默认日期格式
     */
    public static String DEFAULT_DATE_PATTERN="yyyy年MM月dd日";


    /**
     * 获取WorkBook
     * @param path
     * @return
     */
    public static Workbook getWorkBook(String path){
        Workbook workbook = null;
        int lastIndexOf = path.lastIndexOf(".");
        String suffixName = path.substring(lastIndexOf);
        if (StringUtils.isNotBlank(suffixName)){
            if (suffixName.equals(EXCEL_2003)){
                workbook = new HSSFWorkbook();
            }else if (suffixName.equals(EXCEL_2007)){
                workbook = new XSSFWorkbook();
            }
        }
        return workbook;
    }


    /**
     * 生成文件流
     * @param response
     * @param workbook
     * @param excelName 导出的excel文件名
     **/
    public static void generateOutputStream(HttpServletResponse response, @NotNull HSSFWorkbook workbook, String excelName){
        try (OutputStream os = response.getOutputStream()) {
            response.setContentType("application/vnd.ms-excel;");
            response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
            response.setHeader("Content-Disposition", "attachment;filename=" + new String(excelName.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1));
            response.addHeader("Cache-Control", "no-cache");
            workbook.write(os);
            os.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
