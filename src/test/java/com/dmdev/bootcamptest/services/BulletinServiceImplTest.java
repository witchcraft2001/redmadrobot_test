package com.dmdev.bootcamptest.services;

import com.dmdev.bootcamptest.data.mappers.BulletinMapper;
import com.dmdev.bootcamptest.data.models.Bulletin;
import com.dmdev.bootcamptest.data.models.Role;
import com.dmdev.bootcamptest.data.models.Tag;
import com.dmdev.bootcamptest.data.models.User;
import com.dmdev.bootcamptest.repositories.BulletinRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.access.AccessDeniedException;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static com.dmdev.bootcamptest.data.constants.SecurityConstants.ADMIN_ROLE_NAME;
import static com.dmdev.bootcamptest.data.constants.SecurityConstants.USER_ROLE_NAME;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BulletinServiceImplTest {
    private Bulletin bul1, bul2, bul3, bul4;
    private User user1, user2, user3;
    private Tag tag1, tag2, tag3;

    @MockBean
    private UserService userService;

    @MockBean
    private BulletinRepository bulletinRepository;

    @MockBean
    private TagService tagService;

    @MockBean
    private ImageService imageService;

    @MockBean
    private BulletinMapper bulletinMapper;

    private BulletinService service;

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

        tag1 = new Tag(1L, "Tag1", null);
        tag2 = new Tag(2L, "Tag2", null);
        tag3 = new Tag(3L, "Tag3", null);

        bul1 = Bulletin.builder()
                .id(1L)
                .author(user1)
                .isPublished(true)
                .isActive(false)
                .tags(Set.of(tag1, tag2))
                .build();

        bul2 = Bulletin.builder()
                .id(2L)
                .isPublished(true)
                .isActive(true)
                .author(user2)
                .tags(Set.of(tag1, tag3))
                .build();

        bul3 = Bulletin.builder()
                .id(3L)
                .isPublished(false)
                .isActive(true)
                .author(user3)
                .tags(Set.of(tag3, tag2))
                .build();

        bul4 = Bulletin.builder()
                .id(4L)
                .isPublished(true)
                .isActive(true)
                .author(user3)
                .tags(Set.of(tag2, tag3))
                .build();

        Mockito.when(userService.findByEmail(user1.getEmail())).thenReturn(Optional.ofNullable(user1));
        Mockito.when(userService.findByEmail(user2.getEmail())).thenReturn(Optional.ofNullable(user2));

        Mockito.when(bulletinRepository.findAll()).thenReturn(List.of(bul1, bul2, bul3, bul4));
        Mockito.when(bulletinRepository.findById(bul1.getId())).thenReturn(Optional.ofNullable(bul1));
        Mockito.when(bulletinRepository.findById(bul2.getId())).thenReturn(Optional.ofNullable(bul2));
        service = new BulletinServiceImpl(bulletinRepository, tagService, imageService, bulletinMapper, userService);

    }

    @Test
    void deleteById() {
        // Given
        // When
        service.deleteById(bul1.getId(), user1.getEmail());
        // Then
    }

    @Test
    void deleteSomeoneElseMessage() {
        // Given
        // When
        Exception exception = assertThrows(AccessDeniedException.class, () -> service.deleteById(bul1.getId(), user2.getEmail()));
        // Then
        String expectedMessage = "You can't delete this bulletin";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void deleteSomeoneElseMessageByAdmin() {
        // Given
        // When
        service.deleteById(bul2.getId(), user1.getEmail());
        // Then
    }

    @Test
    void getPublished() {
        // Given
        // When
        List<Bulletin> items = service.getPublished(null);
        // Then
        assertEquals(2, items.size());
        assertTrue(items.stream().allMatch(bulletin -> bulletin.isPublished() && bulletin.isActive()));
    }

    @Test
    void getPublishedFilteredByTags() {
        // Given
        // When
        List<Bulletin> items = service.getPublished(List.of(tag2.getName()));
        // Then
        assertEquals(1, items.size());
        assertTrue(items.stream().allMatch(bulletin -> bulletin.isPublished() && bulletin.isActive() && bulletin.getTags().contains(tag2)));
    }
}