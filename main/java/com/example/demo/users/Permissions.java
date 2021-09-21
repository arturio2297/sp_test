package com.example.demo.users;

public enum Permissions {

    READ("read"),
    WRITE("write"),
    DELETE("delete");

    private final String permission;

    Permissions(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return this.permission;
    }
}
