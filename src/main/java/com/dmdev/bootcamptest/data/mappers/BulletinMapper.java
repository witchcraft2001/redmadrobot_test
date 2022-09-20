package com.dmdev.bootcamptest.data.mappers;

import com.dmdev.bootcamptest.data.dto.BulletinDto;
import com.dmdev.bootcamptest.data.models.Bulletin;
import com.dmdev.bootcamptest.data.models.Image;
import com.dmdev.bootcamptest.data.models.Tag;
import org.springframework.stereotype.Component;

@Component
public class BulletinMapper {
    public Bulletin toModel(BulletinDto dto) {
        return Bulletin
                .builder()
                .contacts(dto.getContacts())
                .description(dto.getDescription())
                .title(dto.getTitle())
                .isPublished(dto.isPublished())
                .build();
    }

    public BulletinDto toDto(Bulletin model) {
        return new BulletinDto(
                model.getId(),
                model.getTitle(),
                model.getDescription(),
                model.getContacts(),
                model.isActive(),
                model.isPublished(),
                model.getImages().stream().map(Image::getUrl).toArray(String[]::new),
                model.getTags().stream().map(Tag::getName).toArray(String[]::new),
                model.getAuthor().getId(),
                model.getAuthor().getUsername()
        );
    }
}
