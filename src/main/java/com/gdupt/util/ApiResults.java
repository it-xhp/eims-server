package com.gdupt.util;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author xuhuaping
 * @date 2022/9/13
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApiResults<T> {
    private String message;
    private boolean success;
    private Integer errorCode;
    private T data;
}
