package com.dmdev.bootcamptest.services;

import com.dmdev.bootcamptest.data.models.Bulletin;
import com.dmdev.bootcamptest.data.models.Message;
import com.dmdev.bootcamptest.data.models.User;
import com.dmdev.bootcamptest.exceptions.BulletinNotFoundException;
import com.dmdev.bootcamptest.exceptions.MessageNotFoundException;
import com.dmdev.bootcamptest.exceptions.UnknownUserException;
import com.dmdev.bootcamptest.repositories.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.dmdev.bootcamptest.data.constants.SecurityConstants.ADMIN_ROLE_NAME;

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

    @Override
    public void deleteById(long id, String email) {
        Optional<Message> optionalMessage = repository.findById(id);
        if (optionalMessage.isEmpty()) {
            throw new MessageNotFoundException("A message with ID=" + id + " doesn't found");
        }

        Optional<User> userOptional = userService.findByEmail(email);
        if (userOptional.isEmpty()) {
            throw new UnknownUserException("Unknown user");
        }

        Message message = optionalMessage.get();
        if (!message.getSender().getEmail().equalsIgnoreCase(email) ||
                userOptional.get().getRoles().stream().noneMatch(role -> role.getName().equalsIgnoreCase(ADMIN_ROLE_NAME))) {
            throw new AccessDeniedException("You can't delete this message");
        }
        repository.deleteById(id);
    }
}
