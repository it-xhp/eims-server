package com.gdupt.enums;

/**
 * @author xuhuaping
 * @date 2022/9/13
 * 错误码枚举类
 */
public enum ErrorCodeEnum {

    /**
     * 不合法的参数
     */
    INVALID_PARAM(10001, "不合法的参数"),

    /**
     * 找不到对应的数据
     */
    DATA_NOT_FOUND(20001, "找不到对应的数据"),

    /**
     * 重复数据
     */
    DUPLICATE_DATA(20002, "重复数据"),

    /**
     * 不允许进行该数据操作
     */
    DATA_OPERATION_DENY(20003, "不允许进行该数据操作"),

    /**
     * 数据操作失败
     */
    DATA_OPERATION_FAIL(20004, "数据操作失败"),

    /**
     * 系统异常
     */
    SYSTEM_ERROR(30001, "系统异常"),

    /**
     * 接口返回的数据错误
     */
    API_RESPONSE_ERROR(30011, "接口返回的数据错误"),


    /**
     * 登录失败
     */
    LOGIN_FAIL(50001, "登录失败"),
    /**
     * 登录已失效
     */
    NO_AUTHORIZATION(50012, "登录已失效"),

    /**
     * 客户端未授权
     */
    CLIENT_NOT_AUTHORIZED(50100, "客户端未授权"),
    ;

    /**
     * 错误码
     */
    private int errorCode;
    /**
     * 错误描述
     */
    private String description;

    ErrorCodeEnum(int errorCode, String description) {
        this.errorCode = errorCode;
        this.description = description;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getDescription() {
        return description;
    }

}
