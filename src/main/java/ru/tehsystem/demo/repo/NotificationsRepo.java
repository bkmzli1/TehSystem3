package ru.tehsystem.demo.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.tehsystem.demo.domain.Img;
import ru.tehsystem.demo.domain.Notifications;

public interface NotificationsRepo extends JpaRepository<Notifications, String> {


}
