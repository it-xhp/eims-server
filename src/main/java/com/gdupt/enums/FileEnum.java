package com.gdupt.enums;

/**
 * @author xuhuaping
 * @date 2022/11/17
 * 文件枚举类
 */
public enum FileEnum {
    /**
     * 头像照片
     */
    PHOTOGRAPH(1,"头像照片"),
    /**
     * 请假附件
     */
    HOLIDAY(2,"请假附件")
    ;
    /**
     * 附件id
     */
    private int relationTypeId;

    /**
     * 附件描述
     */
    private String description;

    FileEnum(int relationTypeId, String description) {
        this.relationTypeId = relationTypeId;
        this.description = description;
    }
}
