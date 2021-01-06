package ru.tehsystem.demo.domain.enums;

public enum Role {
    EXECUTOR("EXECUTOR"), USER("USER"), ADMIN("ADMIN");

    String role;

    Role(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
