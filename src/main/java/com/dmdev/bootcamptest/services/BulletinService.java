package com.dmdev.bootcamptest.services;

import com.dmdev.bootcamptest.data.dto.BulletinDto;
import com.dmdev.bootcamptest.data.models.Bulletin;

import java.util.List;
import java.util.Optional;

public interface BulletinService {
    Optional<Bulletin> findById(long id);
    List<Bulletin> findAll(boolean isAdmin);
    Bulletin save(BulletinDto bulletin, String email);
    void deleteById(long id);
    Optional<Bulletin> publishById(long id);
}
