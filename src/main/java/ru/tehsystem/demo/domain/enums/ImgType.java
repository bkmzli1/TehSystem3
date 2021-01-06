package ru.tehsystem.demo.domain.enums;

public enum ImgType {
    TASK("TASK"),
    TASK_MS("TASK_MS"),
    PROFILE_IMG("PROFILE_IMG"),
    PROFILE_BACKGROUND("PROFILE_BACKGROUND");

    String type;

    ImgType(String type) {
        this.type = type;
    }
}
