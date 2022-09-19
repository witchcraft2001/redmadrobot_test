package com.dmdev.bootcamptest.repositories;

import com.dmdev.bootcamptest.data.models.Image;
import org.springframework.data.repository.CrudRepository;

public interface ImageRepository extends CrudRepository<Image, Long> {
}
