package ru.tehsystem.demo.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.tehsystem.demo.domain.Massages;

public interface MassagesRepo extends JpaRepository<Massages, String> {

}
