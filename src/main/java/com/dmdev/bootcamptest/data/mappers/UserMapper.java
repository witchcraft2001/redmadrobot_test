package com.dmdev.bootcamptest.data.mappers;

import com.dmdev.bootcamptest.data.dto.UserDto;
import com.dmdev.bootcamptest.data.models.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public User toModel(UserDto dto) {
        return User
                .builder()
                .email(dto.getEmail())
                .password(dto.getPassword())
                .isActive(dto.isActive())
                .build();
    }
}
