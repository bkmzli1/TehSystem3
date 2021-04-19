package ru.tehsystem.demo.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.tehsystem.demo.domain.Notifications;
import ru.tehsystem.demo.domain.User;
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
    public Object notifications(Authentication authentication) throws InterruptedException {
        try {
            User user = (User) authentication.getPrincipal();
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
}
