package com.dmdev.bootcamptest.repositories;

import com.dmdev.bootcamptest.data.models.Tag;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface TagRepository extends CrudRepository<Tag, Long> {
    Optional<Tag> findTagByName(String name);
}
