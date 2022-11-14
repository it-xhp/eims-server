package com.gdupt.entity;

import lombok.*;

import java.util.Date;

/**
 * @author xuhuaping
 * @date 2022/11/7
 */
@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Holiday {
    private Integer id;
    private String key;
    private String title;
    private String remark;
    private Date startDay;
    private Date endDay;
    private Integer day;
    private Integer status;
    private Date createTime;
    private Integer creator;
    private Integer delete;
}
