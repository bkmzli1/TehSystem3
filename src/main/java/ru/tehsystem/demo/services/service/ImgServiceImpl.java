package ru.tehsystem.demo.services.service;

import org.springframework.stereotype.Service;
import ru.tehsystem.demo.domain.Img;
import ru.tehsystem.demo.model.ImgCreateModel;
import ru.tehsystem.demo.repo.ImgRepo;
import ru.tehsystem.demo.services.impl.ImgService;

@Service
public class ImgServiceImpl implements ImgService {
    private final ImgRepo imgRepo;

    public ImgServiceImpl(ImgRepo imgRepo) {

        this.imgRepo = imgRepo;
    }

    @Override
    public Img imgCrate(ImgCreateModel taskCreate) {
        Img img = new Img();
        img.setImg(taskCreate.getImg());
        img.setImgType(taskCreate.getImgType());
        img.setName(taskCreate.getName());
        return img;
    }

    @Override
    public Img imgCrate(Img img) {
        imgRepo.save(img);
        return null;
    }

}
