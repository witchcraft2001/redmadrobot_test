package com.dmdev.bootcamptest.data.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BulletinDto {
    private Long id;
    @NotBlank(message = "Title can't be empty")
    private String title;
    @NotBlank(message = "Description can't be empty")
    private String description;
    private String contacts;
    private boolean isActive;
    private boolean isPublished;
    private String[] images;
    private String[] tags;
    private long authorId;
    private String author;
}
