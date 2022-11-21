package com.gdupt.enums;

/**
 * @author xuhuaping
 * @date 2022/11/21
 */
public enum  RoleEnum {
    /**
     * 员工
     */
    STAFF(1,"员工"),
    /**
     * 经理
     */
    MANAGER(2,"经理"),
    /**
     * 管理员
     */
    ADMIN(3,"管理员")
    ;

    RoleEnum(Integer roleId, String roleName) {
        this.roleId = roleId;
        this.roleName = roleName;
    }

    private Integer roleId;
    private String roleName;

    public Integer getRoleId() {
        return roleId;
    }

    public String getRoleName() {
        return roleName;
    }
}
