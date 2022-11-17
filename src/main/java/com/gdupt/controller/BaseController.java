package com.gdupt.controller;

import cn.hutool.json.JSONObject;
import com.gdupt.constant.ApiConstant;
import com.gdupt.util.PageParam;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;


/**
 * @author xuhuaping
 * @date 2022/9/13
 */
public class BaseController {


    protected PageParam getPageParams(JSONObject pageParamsJson) {
        PageParam pageParam = new PageParam();
        if (pageParamsJson != null && pageParamsJson.size() > 0) {
            Integer currentPage = pageParamsJson.getInt(ApiConstant.CURRENT_PAGE);
            if (currentPage != null) {
                pageParam.setCurrentPage(currentPage);
            } else {
                pageParam.setCurrentPage(1);
            }

            Integer pageSize = pageParamsJson.getInt(ApiConstant.PAGE_SIZE);
            if (pageSize != null) {
                pageParam.setPageSize(pageSize);
            } else {
                pageParam.setPageSize(30);
            }

            JSONObject params = pageParamsJson.getJSONObject(ApiConstant.PARAMS);
            if (params != null) {
                pageParam.setParams(params);
            }

            Map<String, Object> sort = pageParamsJson.getJSONObject(ApiConstant.SORT);
            if (sort != null) {
                pageParam.getSort().putAll(sort);
            }
        }
        return pageParam;
    }


    /**
     * 获取request
     *
     * @return HttpServletRequest
     */
    protected HttpServletRequest getHttpServletRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }
}
