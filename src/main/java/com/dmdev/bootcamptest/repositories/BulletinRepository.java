package com.dmdev.bootcamptest.repositories;

import com.dmdev.bootcamptest.data.models.Bulletin;
import org.springframework.data.repository.CrudRepository;

public interface BulletinRepository extends CrudRepository<Bulletin, Long> {
}
