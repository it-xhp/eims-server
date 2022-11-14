package com.gdupt.entity;

import lombok.*;

/**
 * @author xuhuaping
 * @date 2022/9/20
 */
@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRole {
    private Integer id;
    private Integer userId;
    private Integer roleId;
}
