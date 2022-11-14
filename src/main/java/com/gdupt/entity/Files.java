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
public class Files {
    private Integer fileId;
    private String fileName;
    private String fileSuffix;
    private String path;
    private Integer relationTypeId;
    private Date uploadTime;
    private Integer uploadUserId;
    private Integer relationId;
}
