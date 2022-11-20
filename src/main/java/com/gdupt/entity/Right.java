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
@TableName("emis_right")
public class Right {
    private Integer rightId;
    private Integer order;
    private String rightName;
    private String title;
    private Integer parentId;
    private String routerName;
    private String path;
    private String component;
    private String rightCode;
    private String icon;
}
