package ru.tehsystem.demo.model;

import ru.tehsystem.demo.domain.enums.ImgType;

public class ImgCreateModel {
    String img;
    ImgType imgType;
    String name;

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public ImgType getImgType() {
        return imgType;
    }

    public void setImgType(ImgType imgType) {
        this.imgType = imgType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
