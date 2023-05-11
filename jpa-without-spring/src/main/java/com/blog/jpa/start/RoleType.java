package com.blog.jpa.start;

public enum RoleType {

    ROLE_ADMIN("admin"),
    ROLE_MEMBER("member");

    private String roleType;

    RoleType(String roleType) {
        this.roleType = roleType;
    }

    public String getRoleType() {
        return roleType;
    }
}

