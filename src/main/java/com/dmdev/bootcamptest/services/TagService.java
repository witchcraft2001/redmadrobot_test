package com.dmdev.bootcamptest.services;

import com.dmdev.bootcamptest.data.models.Tag;

import java.util.List;
import java.util.Optional;

public interface TagService {
    Optional<Tag> findByName(String name);
    List<Tag> findAll();
    Tag save(Tag tag);
    Tag update(Tag tag);
}
