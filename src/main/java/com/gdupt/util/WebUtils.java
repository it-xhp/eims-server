package com.gdupt.util;

import cn.hutool.json.JSONUtil;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author xuhuaping
 * @date 2022/11/7
 */
public class WebUtils {
    /**
     * 将结果渲染到客户端
     * @param response 渲染对象
     * @param result 结果
     * @return
     */
    public static String renderString(HttpServletResponse response, ApiResults result) {
        try {
            response.setStatus(200);
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            String resultString = JSONUtil.toJsonStr(result);
            response.getWriter().print(resultString);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
