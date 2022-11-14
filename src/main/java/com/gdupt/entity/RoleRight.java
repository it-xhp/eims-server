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
public class RoleRight {
    private Integer id;
    private Integer roleId;
    private Integer rightId;
}
