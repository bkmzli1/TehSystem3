package ru.tehsystem.demo.controller;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.tehsystem.demo.domain.Notifications;
import ru.tehsystem.demo.domain.User;
import ru.tehsystem.demo.domain.Views;
import ru.tehsystem.demo.repo.NotificationsRepo;
import ru.tehsystem.demo.repo.UserRepo;

import java.util.*;

@RestController
@RequestMapping("/notifications")
@CrossOrigin
public class NotificationsController {
    private final NotificationsRepo notificationsRepo;
    private final UserRepo userRepo;


    public NotificationsController(NotificationsRepo notificationsRepo, UserRepo userRepo) {
        this.notificationsRepo = notificationsRepo;
        this.userRepo = userRepo;
    }

    @GetMapping("")
    @ResponseBody
    @JsonView(Views.NotificationsBasic.class)
    public Object notifications(Authentication authentication) throws InterruptedException {
        try {
            User user = userRepo.findUserById(((User) authentication.getPrincipal()).getId());
            Set<Notifications> notifications = new HashSet<>();
            notifications.addAll(user.getNotifications());

            notifications.removeIf(Notifications::isClose);
            Thread.sleep(1000);
            return notifications.size();
        } catch (NullPointerException nullPointerException) {
            Thread.sleep(1000);
            return 0;
        }

    }

    @GetMapping("/get")
    @ResponseBody
    @JsonView(Views.NotificationsBasic.class)
    public Object notification(Authentication authentication) throws InterruptedException {
        try {
            User user = userRepo.findUserById(((User) authentication.getPrincipal()).getId());

            Set<Notifications> notifications = new TreeSet<>(Comparator.comparing(Notifications::getData));
            notifications.addAll(user.getNotifications());

            notifications.removeIf(Notifications::isClose);
            Thread.sleep(1000);
            return notifications;
        } catch (NullPointerException nullPointerException) {
            Thread.sleep(1000);
            return 0;
        }

    }

    @DeleteMapping("/{id}")
    @ResponseBody
    @JsonView(Views.NotificationsBasic.class)
    public Object deleteNotification(@PathVariable String id, Authentication authentication)
            throws InterruptedException {
        Notifications notifications = notificationsRepo.findById(id).get();
        notifications.setClose(true);
        notificationsRepo.save(notifications);
        return notifications(authentication);
    }
}

