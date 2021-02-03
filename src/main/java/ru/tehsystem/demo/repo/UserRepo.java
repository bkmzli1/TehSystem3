package ru.tehsystem.demo.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.tehsystem.demo.domain.User;
import ru.tehsystem.demo.domain.enums.Role;

import java.util.List;
import java.util.Set;

public interface UserRepo extends JpaRepository<User, String> {

    Set<User> findAllBy();

    User findOneByUsername(String username);

    User findByEmail(String email);

    User findOneById(String id);


    List<User> findUsersByAuthoritiesAuthority(Role authorities);
}
