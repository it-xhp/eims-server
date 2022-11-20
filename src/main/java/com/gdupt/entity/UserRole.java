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
@TableName("emis_user_role")
public class UserRole {
    private Integer id;
    private Integer userId;
    private Integer roleId;
}
