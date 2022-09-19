package com.dmdev.bootcamptest.services;

import com.dmdev.bootcamptest.data.dto.UserDto;
import com.dmdev.bootcamptest.data.models.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User save(UserDto user);
    List<User> findAll();
    Optional<User> findByEmail(String email);
}
