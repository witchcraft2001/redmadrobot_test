package com.dmdev.bootcamptest.services;

import com.dmdev.bootcamptest.data.dto.BulletinDto;
import com.dmdev.bootcamptest.data.models.Bulletin;

import java.util.List;
import java.util.Optional;

public interface BulletinService {
    Optional<Bulletin> findById(long id);
    List<Bulletin> findAll();
    List<Bulletin> findMy(String email);
    List<Bulletin> getPublished(List<String> tags);
    Bulletin save(BulletinDto bulletin, String email);
    void deleteById(long id, String email);
    Bulletin updateActiveBulletin(long id, boolean isActive);
    Bulletin updatePublishedBulletin(long id, String email, boolean isPublished);
}
