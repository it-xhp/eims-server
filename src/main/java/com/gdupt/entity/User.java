package com.gdupt.entity;

import lombok.*;

import java.util.Date;

/**
 * @author xuhuaping
 * @date 2022/9/13
 */
@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private Integer userId;
    private String userName;
    private String password;
    private Integer gender;
    private Integer deptId;
    private Integer isDelete;
    private Integer isLocked;
    private String email;
    private String telephone;
    private Date passwordChangeTime;
    private String province;
    private String city;
    private String country;
    private String address;
    private Date createTime;
    private Date updateTime;
    private Integer loginFailCount;
    private Date lastLoginTime;
}
