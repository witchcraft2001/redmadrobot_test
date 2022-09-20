package com.dmdev.bootcamptest.data.mappers;

import com.dmdev.bootcamptest.data.dto.TagDto;
import com.dmdev.bootcamptest.data.models.Tag;
import org.springframework.stereotype.Component;

@Component
public class TagMapper {
    public Tag toModel(TagDto dto) {
        return new Tag(dto.getId(), dto.getName(), dto.getDescription());
    }

    public TagDto toDto(Tag model) {
        return new TagDto(model.getId(), model.getName(), model.getDescription());
    }
}
