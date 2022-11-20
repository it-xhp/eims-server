package com.gdupt.entity;

import com.baomidou.mybatisplus.annotation.TableName;
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
@TableName("emis_role_right")
public class RoleRight {
    private Integer id;
    private Integer roleId;
    private Integer rightId;
}
