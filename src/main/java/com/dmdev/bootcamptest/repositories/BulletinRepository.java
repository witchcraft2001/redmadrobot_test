package com.dmdev.bootcamptest.repositories;

import com.dmdev.bootcamptest.data.models.Bulletin;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BulletinRepository extends CrudRepository<Bulletin, Long> {
    List<Bulletin> findAllByAuthorId(long id);
}
