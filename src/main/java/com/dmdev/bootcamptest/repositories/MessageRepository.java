package com.dmdev.bootcamptest.repositories;

import com.dmdev.bootcamptest.data.models.Message;
import org.springframework.data.repository.CrudRepository;

public interface MessageRepository extends CrudRepository<Message, Long> {
}
