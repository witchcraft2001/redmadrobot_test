package com.dmdev.bootcamptest.services;

import com.dmdev.bootcamptest.data.models.Tag;
import com.dmdev.bootcamptest.exceptions.TagNameUnavailableException;
import com.dmdev.bootcamptest.exceptions.TagNotFoundException;
import com.dmdev.bootcamptest.repositories.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {
    private final TagRepository repository;

    @Override
    public Optional<Tag> findByName(String name) {
        return repository.findTagByName(name);
    }

    @Override
    public List<Tag> findAll() {
        List<Tag> list = new ArrayList<>();
        repository.findAll().iterator().forEachRemaining(list::add);
        return list;
    }

    @Override
    public Tag save(Tag tag) {
        if (isExists(tag.getName())) {
            throw new TagNameUnavailableException("Tag with name '" + tag.getName() + "' is exists");
        }
        return repository.save(tag);
    }

    @Override
    public Tag update(Tag tag) {
        Optional<Tag> old = repository.findById(tag.getId());
        if (old.isPresent()) {
            Tag model = old.get();
            model.setDescription(tag.getDescription());
            model.setName(tag.getName());
            repository.save(model);
            return model;
        }
        throw new TagNotFoundException("Tag with ID=" + tag.getId() + " isn't found");
    }

    private boolean isExists(String name) {
        return repository.findTagByName(name).isPresent();
    }
}
