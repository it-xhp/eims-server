package com.gdupt.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.util.Date;

/**
 * @author xuhuaping
 * @date 2022/10/14
 */
@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("emis_dept")
public class Dept {
    
    private Integer deptId;
    
    private String deptName;
    
    private Integer leader;
    
    private String phone;
    
    private String email;
    /**
     * 0 正常 1 停用
     */
    private Integer status;
    
    private Integer createBy;
    
    private Date createTime;
    
    private Integer updateBy;
    
    private Date updateTime;
    /**
     * 0 存在 1 删除
     */
    private Integer delete;


}

