package ru.tehsystem.demo.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.tehsystem.demo.domain.Img;

public interface ImgRepo extends JpaRepository<Img, String> {


}
