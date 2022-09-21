package com.dmdev.bootcamptest.services;

import com.dmdev.bootcamptest.data.dto.BulletinDto;
import com.dmdev.bootcamptest.data.mappers.BulletinMapper;
import com.dmdev.bootcamptest.data.models.*;
import com.dmdev.bootcamptest.exceptions.BulletinNotFoundException;
import com.dmdev.bootcamptest.exceptions.UnknownUserException;
import com.dmdev.bootcamptest.repositories.BulletinRepository;
import com.dmdev.bootcamptest.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.*;

@Service
@RequiredArgsConstructor
public class BulletinServiceImpl implements BulletinService {
    private final BulletinRepository repository;
    private final TagService tagService;
    private final ImageService imageService;
    private final BulletinMapper mapper;
    private final UserRepository userRepository;

    @Override
    public Optional<Bulletin> findById(long id) {
        return repository.findById(id);
    }

    @Override
    public List<Bulletin> findMy(String email) {
        List<Bulletin> list = new ArrayList<>();
        repository.findAll().iterator().forEachRemaining(item -> {
            if (item.getAuthor().getEmail().equalsIgnoreCase(email)) list.add(item);
        });
        return list;
    }

    @Override
    public List<Bulletin> findAll() {
        List<Bulletin> list = new ArrayList<>();
        repository.findAll().iterator().forEachRemaining(list::add);
        return list;
    }

    @Override
    public List<Bulletin> getPublished() {
        List<Bulletin> list = new ArrayList<>();
        repository.findAll().iterator().forEachRemaining(item -> {
            if (item.isActive() && item.isPublished()) list.add(item);
        });
        return list;
    }

    @Override
    public Bulletin save(BulletinDto dto, String email) {
        Optional<User> optional = userRepository.findByEmail(email);
        if (optional.isPresent()) {
            Bulletin model = mapper.toModel(dto);
            model.setAuthor(optional.get());
            model.setCreatedAt(Date.from(Instant.now()));
            //Постмодерация
            model.setActive(true);
            Set<Tag> tags = new HashSet<>();
            if (dto.getTags() != null && dto.getTags().length > 0) {
                Arrays.stream(dto.getTags()).forEach(
                        name -> {
                            Optional<Tag> tag = tagService.findByName(name);
                            tag.ifPresent(tags::add);
                        }
                );
            }
            model.setTags(tags);
            model = repository.save(model);

            List<Image> images = new ArrayList<>();
            if (dto.getImages() != null && dto.getImages().length > 0) {
                Bulletin finalModel = model;
                Arrays.stream(dto.getImages()).forEach(
                        url -> {
                            Image image = imageService.save(
                                    Image.builder()
                                            .url(url)
                                            .bulletin(finalModel)
                                            .author(optional.get())
                                            .build());
                            images.add(image);
                        }
                );
            }

            model.setImages(images);
            return model;
        }
        throw new UnknownUserException("A user with email '" + email + "' doesn't exists");
    }

    @Override
    public void deleteById(long id) {
        repository.deleteById(id);
    }

    @Override
    public Bulletin updateActiveBulletin(long id, boolean isActive) {
        Optional<Bulletin> optional = repository.findById(id);
        if (optional.isPresent()) {
            Bulletin bulletin = optional.get();
            bulletin.setActive(isActive);
            repository.save(bulletin);
            return bulletin;
        }
        throw new BulletinNotFoundException("A bulletin with ID=" + id + " doesn't found");
    }

    @Override
    public Bulletin updatePublishedBulletin(long id, String email, boolean isPublished) {
        Optional<Bulletin> optional = repository.findById(id);
        if (optional.isPresent()) {
            Optional<User> userOptional = userRepository.findByEmail(email);
            if (userOptional.isEmpty()) {
                throw new UnknownUserException("Unknown user");
            }
            Bulletin bulletin = optional.get();
            if (Objects.equals(userOptional.get().getId(), bulletin.getAuthor().getId())) {
                bulletin.setPublished(isPublished);
                repository.save(bulletin);
                return bulletin;
            }
            throw new AccessDeniedException("You can't modify this bulletin");
        }
        throw new BulletinNotFoundException("A bulletin with ID=" + id + " doesn't found");
    }

    private boolean isAdmin(String email) {
        Optional<User> optional = userRepository.findByEmail(email);
        return optional.isPresent() &&
                optional.get().getRoles().stream().anyMatch(role -> role.getName().equalsIgnoreCase("ADMIN"));
    }
}
