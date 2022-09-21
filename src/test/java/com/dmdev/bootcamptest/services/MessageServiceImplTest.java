package com.dmdev.bootcamptest.services;

import com.dmdev.bootcamptest.data.models.Bulletin;
import com.dmdev.bootcamptest.data.models.Message;
import com.dmdev.bootcamptest.data.models.Role;
import com.dmdev.bootcamptest.data.models.User;
import com.dmdev.bootcamptest.repositories.MessageRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.access.AccessDeniedException;

import java.sql.Date;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static com.dmdev.bootcamptest.data.constants.SecurityConstants.ADMIN_ROLE_NAME;
import static com.dmdev.bootcamptest.data.constants.SecurityConstants.USER_ROLE_NAME;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MessageServiceImplTest {

    private Message msg1, msg2, msg3;
    private Bulletin bul1, bul2;
    private User user1, user2, user3;

    @MockBean
    private MessageRepository repository;
    private MessageService service;
    @MockBean
    private BulletinService bulletinService;
    @MockBean
    private UserService userService;

    @BeforeEach
    void setUp() {
        user1 = User.builder()
                .id(1L)
                .isActive(true)
                .email("user@domain.com")
                .roles(Set.of(new Role(1, ADMIN_ROLE_NAME)))
                .build();

        user2 = User.builder()
                .id(2L)
                .isActive(true)
                .email("user2@domain.com")
                .roles(Set.of(new Role(1, USER_ROLE_NAME)))
                .build();

        user3 = User.builder()
                .id(3L)
                .isActive(true)
                .email("user3@domain.com")
                .roles(Set.of(new Role(1, USER_ROLE_NAME)))
                .build();

        bul1 = Bulletin.builder()
                .id(1L)
                .author(user2)
                .build();

        bul2 = Bulletin.builder()
                .id(2L)
                .author(user2)
                .build();

        msg1 = new Message(1L, user1, user2, "Text", bul1, Date.from(Instant.now()));
        msg2 = new Message(2L, user2, user1, "Text", bul1, Date.from(Instant.now()));
        msg3 = new Message(3L, user2, user3, "Text", bul1, Date.from(Instant.now()));

        Mockito.when(repository.findAll()).thenReturn(List.of(msg1, msg2, msg3));
        Mockito.when(repository.findById(msg1.getId())).thenReturn(Optional.ofNullable(msg1));
        Mockito.when(repository.findById(msg2.getId())).thenReturn(Optional.ofNullable(msg2));
        Mockito.when(repository.findAllByBulletinId(bul1.getId())).thenReturn(List.of(msg1, msg2, msg3));
        Mockito.when(userService.findByEmail(user1.getEmail())).thenReturn(Optional.ofNullable(user1));
        Mockito.when(userService.findByEmail(user2.getEmail())).thenReturn(Optional.ofNullable(user2));
        service = new MessageServiceImpl(repository, bulletinService, userService);
    }

    @Test
    void findAllMyMessagesByBulletinId() {
        // Given
        // When
        List<Message> items = service.findAllMyMessagesByBulletinId(bul1.getId(), user1.getEmail());
        // Then
        boolean isMyMessages = items.stream().allMatch(msg ->
                msg.getSender().getEmail().equalsIgnoreCase(user1.getEmail()) ||
                        msg.getRecipient().getEmail().equalsIgnoreCase(user1.getEmail())
        );

        assertEquals(2, items.size());
        assertTrue(isMyMessages);
    }

    @Test
    void deleteMessageByOwner() {
        // Given
        // When
        service.deleteById(msg2.getId(), user2.getEmail());
        // Then
    }

    @Test
    void deleteSomeoneElseMessage() {
        // Given
        // When
        Exception exception = assertThrows(AccessDeniedException.class, () -> {
            service.deleteById(msg1.getId(), user2.getEmail());
        });
        // Then
        String expectedMessage = "You can't delete this message";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void deleteSomeoneElseMessageByAdmin() {
        // Given
        // When
        service.deleteById(msg2.getId(), user1.getEmail());
        // Then
    }
}