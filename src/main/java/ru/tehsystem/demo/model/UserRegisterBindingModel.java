package ru.tehsystem.demo.model;

import ru.tehsystem.demo.model.validations.IsEmailRegistered;
import ru.tehsystem.demo.model.validations.IsPasswordMatching;
import ru.tehsystem.demo.model.validations.IsUsernameTaken;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@IsPasswordMatching
public class UserRegisterBindingModel {
    @Size(min = 4, max = 20, message = "Имя пользователя должно быть от 4 до 20 символов")
    @IsUsernameTaken
    private String username;

    @Size(min = 4, max = 30, message = "Пароль должно быть от 4 до 30 символов")
    private String password;

    private String confirmPassword;

    @NotEmpty(message = "Укажите почту")
    @Email(message = "Неправильная почта")
    @IsEmailRegistered
    private String email;

    private String img;
    private String imgFon;


    private boolean admin;
    private boolean executor;
    private String firstName;
    private String lastName;
    private String middleName;
    private String oldPassword;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public boolean isExecutor() {
        return executor;
    }

    public void setExecutor(boolean executor) {
        this.executor = executor;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getImgFon() {
        return imgFon;
    }

    public void setImgFon(String imgFon) {
        this.imgFon = imgFon;
    }



    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }
}