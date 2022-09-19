package com.dmdev.bootcamptest.repositories;

import com.dmdev.bootcamptest.data.models.Tag;
import org.springframework.data.repository.CrudRepository;

public interface TagRepository extends CrudRepository<Tag, Long> {
}
