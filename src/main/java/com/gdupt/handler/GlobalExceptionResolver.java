package com.gdupt.handler;

import com.gdupt.enums.ErrorCodeEnum;
import com.gdupt.util.ApiResultUtils;
import com.gdupt.util.ApiResults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * @author xuhuaping
 * @date 2022/10/17
 * 全局异常处理类
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionResolver {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ApiResults processException(Exception ex){
        log.error("捕获全局异常：",ex.getLocalizedMessage());
        return ApiResultUtils.getFail(ErrorCodeEnum.SYSTEM_ERROR,"未知异常，请联系管理员");
    }
}