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
public class Log {
    private Integer logId;
    private String logType;
    private String logInfo;
    private Integer operatorId;
    private String operatorName;
    private Date createTime;
    private String remoteAddress;
    private String browserName;
    private String dataBeforeUpdate;
    private String dataAfterUpdate;
    private String Module;
}
