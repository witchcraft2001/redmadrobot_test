package com.dmdev.bootcamptest.repositories;

import com.dmdev.bootcamptest.data.models.Message;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MessageRepository extends CrudRepository<Message, Long> {
    List<Message> findAllByBulletinId(long bulletinId);
}
