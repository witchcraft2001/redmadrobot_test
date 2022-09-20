package com.dmdev.bootcamptest.services;

import com.dmdev.bootcamptest.data.dto.BulletinDto;
import com.dmdev.bootcamptest.data.mappers.BulletinMapper;
import com.dmdev.bootcamptest.data.models.*;
import com.dmdev.bootcamptest.exceptions.UnknownUserException;
import com.dmdev.bootcamptest.repositories.BulletinRepository;
import com.dmdev.bootcamptest.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
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
        return Optional.empty();
    }

    @Override
    public List<Bulletin> findAll(boolean isAdmin) {
        List<Bulletin> list = new ArrayList<>();
        if (isAdmin) {
            repository.findAll().iterator().forEachRemaining(item -> {
                if (isAdmin || item.isPublished()) list.add(item);
            });
        }
        return list;
    }

    @Override
    public Bulletin save(BulletinDto dto, String email) {
        Optional<User> optional = userRepository.findByEmail(email);
        if (optional.isPresent()) {
            Bulletin model = mapper.toModel(dto);
            model.setAuthor(optional.get());
            model.setPublished(false);
            model.setCreatedAt(Date.from(Instant.now()));
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
    public Optional<Bulletin> publishById(long id) {
        return Optional.empty();
    }
}
