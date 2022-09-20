package com.dmdev.bootcamptest.services;

import com.dmdev.bootcamptest.data.models.Image;

import java.util.List;

public interface ImageService {
    List<Image> findAll();
    Image save(Image Image);
}
