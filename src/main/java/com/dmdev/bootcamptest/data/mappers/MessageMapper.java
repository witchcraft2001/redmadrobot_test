package com.dmdev.bootcamptest.data.mappers;

import com.dmdev.bootcamptest.data.dto.MessageDto;
import com.dmdev.bootcamptest.data.models.Bulletin;
import com.dmdev.bootcamptest.data.models.Message;
import com.dmdev.bootcamptest.data.models.User;
import org.springframework.stereotype.Component;

@Component
public class MessageMapper {
    public Message toModel(MessageDto dto, Bulletin bulletin, User sender, User recipient) {
        return new Message(
                dto.getId(),
                sender,
                recipient,
                dto.getBody(),
                bulletin,
                dto.getCreatedAt());
    }

    public MessageDto toDto(Message model) {
        return new MessageDto(
                model.getId(),
                model.getBody(),
                model.getBulletin().getId(),
                model.getSender().getId(),
                model.getSender().getUsername(),
                model.getRecipient().getId(),
                model.getRecipient().getUsername(),
                model.getCreatedAt());
    }
}
