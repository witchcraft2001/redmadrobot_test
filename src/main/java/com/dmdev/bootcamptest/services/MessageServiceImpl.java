package com.dmdev.bootcamptest.services;

import com.dmdev.bootcamptest.data.models.Bulletin;
import com.dmdev.bootcamptest.data.models.Message;
import com.dmdev.bootcamptest.data.models.User;
import com.dmdev.bootcamptest.exceptions.BulletinNotFoundException;
import com.dmdev.bootcamptest.exceptions.UnknownUserException;
import com.dmdev.bootcamptest.repositories.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {
    private final MessageRepository repository;
    private final BulletinService bulletinService;
    private final UserService userService;

    @Override
    public List<Message> findAllMyMessagesByBulletinId(long bulletinId, String email) {
        List<Message> messages = repository.findAllByBulletinId(bulletinId);

        return messages.stream().filter(item ->
                item.getRecipient().getEmail().equalsIgnoreCase(email) ||
                        item.getSender().getEmail().equalsIgnoreCase(email)
        ).collect(Collectors.toList());
    }

    @Override
    public Message addMessageToBulletin(long bulletinId, long recipientId, String email, String body) {
        Optional<User> senderOptional = userService.findByEmail(email);
        Optional<User> recipientOptional = userService.findById(recipientId);
        Optional<Bulletin> optional = bulletinService.findById(bulletinId);
        if (optional.isEmpty()) {
            throw new BulletinNotFoundException("A bulletin with ID=" + bulletinId + " doesn't found");
        }

        if (senderOptional.isEmpty()) {
            throw new UnknownUserException("Unknown sender");
        }
        if (recipientOptional.isEmpty()) {
            throw new UnknownUserException("Unknown recipient");
        }

        Message message = new Message(
                null,
                senderOptional.get(),
                recipientOptional.get(),
                body,
                optional.get(),
                Date.from(Instant.now()));

        repository.save(message);

        return message;
    }

    // TODO: Add check for message owner
    @Override
    public void deleteById(long id) {
        repository.deleteById(id);
    }
}
