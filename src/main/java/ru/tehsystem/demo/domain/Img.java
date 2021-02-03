package ru.tehsystem.demo.domain;

import org.hibernate.annotations.GenericGenerator;
import ru.tehsystem.demo.domain.enums.ImgType;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "img")
public class Img {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    private String id;
    private String name;
    private String img;
    private ImgType imgType;





    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ImgType getImgType() {
        return imgType;
    }

    public void setImgType(ImgType imgType) {
        this.imgType = imgType;
    }
}
