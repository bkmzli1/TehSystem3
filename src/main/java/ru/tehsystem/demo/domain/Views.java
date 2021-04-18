package ru.tehsystem.demo.domain;


import com.fasterxml.jackson.annotation.JsonView;

public final class Views {

    public interface Id {}

    //User
    public interface UserBasic extends Id {}

    public interface UserAll extends UserBasic, ImgAll ,NotificationsBasic{}

    //Img
    public interface ImgBasic extends Id {}

    public interface ImgAll extends ImgBasic {}

    //Task
    public interface TaskBasic extends UserBasic {}

    public interface TaskAll extends TaskBasic, UserBasic, ImgAll, MassagesBasic {}

    //Massages
    public interface MassagesBasic extends Id, ImgBasic {}

    //Notifications
    public interface NotificationsBasic extends Id {}
}

