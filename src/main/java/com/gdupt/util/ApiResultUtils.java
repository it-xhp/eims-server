package com.gdupt.util;

import com.gdupt.enums.ErrorCodeEnum;


/**
 * @author xuhuaping
 * @date 2022/9/13
 */
public class ApiResultUtils {

    /**
     * 生成“失败”返回值模板
     * @param errorCodeEnum		错误码枚举
     * @return					“失败”返回值模板
     */
    public static ApiResults getFail(ErrorCodeEnum errorCodeEnum) {
        return getFail(errorCodeEnum, "");
    }

    /**
     * 生成“失败”返回值模板
     * @param errorCodeEnum		错误码枚举
     * @param message			自定义信息
     * @return					“失败”返回值模板
     */
    public static ApiResults getFail(ErrorCodeEnum errorCodeEnum, String message) {
        return ApiResults.builder()
                .success(false)
                .message(message==null?"":message)
                .errorCode(errorCodeEnum.getErrorCode())
                .build();

    }

    public static ApiResults getSuccess() {
        return ApiResults.builder()
                .success(true)
                .message("")
                .errorCode(0)
                .build();
    }

    public static<T> ApiResults getSuccess(T data) {
        return ApiResults.builder()
                .success(true)
                .message("")
                .errorCode(0)
                .data(data)
                .build();
    }

    public static ApiResults getSuccess(String message) {
        return ApiResults.builder()
                .success(false)
                .message(message)
                .errorCode(0)
                .build();
    }

    public static<T> ApiResults getSuccess(String message, T data) {
        return ApiResults.builder()
                .success(true)
                .message(message)
                .errorCode(0)
                .data(data)
                .build();
    }
}
