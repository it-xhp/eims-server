package com.gdupt.entity;

import lombok.*;

import java.util.Date;

/**
 * @author xuhuaping
 * @date 2022/9/20
 */
@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Role {
    private Integer roleId;
    private String roleName;
    private String remark;
    private Integer isDelete;
    private Date createTime;
    private Date updateTime;
}
