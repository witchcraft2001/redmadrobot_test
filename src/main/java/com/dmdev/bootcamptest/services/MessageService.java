package com.dmdev.bootcamptest.services;

import com.dmdev.bootcamptest.data.models.Message;

import java.util.List;

public interface MessageService {
    List<Message> findAllMyMessagesByBulletinId(long bulletinId, String email);
    Message addMessageToBulletin(long bulletinId, long recipientId, String email, String message);
    void deleteById(long id, String email);
}
