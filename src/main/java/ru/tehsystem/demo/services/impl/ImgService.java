package ru.tehsystem.demo.services.impl;

import ru.tehsystem.demo.domain.Img;
import ru.tehsystem.demo.model.ImgCreateModel;

public interface ImgService {
   Img imgCrate(ImgCreateModel taskCreate);
   Img imgCrate(Img img);
}
