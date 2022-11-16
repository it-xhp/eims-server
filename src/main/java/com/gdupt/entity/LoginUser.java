package com.gdupt.entity;

import lombok.*;

/**
 * @author XHP
 * @date 2022/11/16
 */
@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginUser {
    private String userName;
    private String password;
    private Integer isRemember;
}
