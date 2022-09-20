package com.dmdev.bootcamptest.data.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BulletinDto {
    private Long id;
    private String title;
    private String description;
    private String contacts;
    private boolean isActive;
    private boolean isPublished;
    private String[] images;
    private String[] tags;
    private Long authorId;
    private String author;
}
