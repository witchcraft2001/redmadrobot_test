package com.dmdev.bootcamptest.services;

import com.dmdev.bootcamptest.data.models.Image;
import com.dmdev.bootcamptest.repositories.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {
    private final ImageRepository repository;

    @Override
    public List<Image> findAll() {
        List<Image> list = new ArrayList<>();
        repository.findAll().iterator().forEachRemaining(list::add);
        return list;
    }

    @Override
    public Image save(Image Image) {
        return repository.save(Image);
    }
}
