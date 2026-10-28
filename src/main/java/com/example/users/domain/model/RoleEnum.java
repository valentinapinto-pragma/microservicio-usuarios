package com.example.users.domain.model;

public enum RoleEnum {
    ADMIN(1L, "ROLE_ADMIN"),
    OWNER(2L, "ROLE_OWNER"),
    EMPLOYEE(3L, "ROLE_EMPLOYEE"),
    CLIENT(4L, "ROLE_CLIENT");

    private final Long id;
    private final String roleName;

    RoleEnum(Long id, String roleName) {
        this.id = id;
        this.roleName = roleName;
    }

    public Long getId() {
        return id;
    }

    public String getRoleName() {
        return roleName;
    }

    public static RoleEnum fromId(Long id) {
        for (RoleEnum role : values()) {
            if (role.id.equals(id)) {
                return role;
            }
        }
        return null;
    }
}
