package ru.tehsystem.demo.model;

import ru.tehsystem.demo.domain.enums.Level;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Set;


public class TaskCreate {
    @NotEmpty(message = "Укажите тип заказа")
    String level;
    @Size(min = 4, max = 255, message = "название должно быть от 4 до 255 символов")
    @NotEmpty(message = "Укажите название заказа")
    String name;
    @Size(min = 4, max = 255, message = "текст должно быть от 4 до 255 символов")
    @NotEmpty(message = "Укажите текст заказа")
    String text;

    Set<String> executor;
    Set<String> imgs;

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public Set<String> getExecutor() {
        return executor;
    }

    public void setExecutor(Set<String> executor) {
        this.executor = executor;
    }

    public Set<String> getImgs() {
        return imgs;
    }

    public void setImgs(Set<String> imgs) {
        this.imgs = imgs;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
