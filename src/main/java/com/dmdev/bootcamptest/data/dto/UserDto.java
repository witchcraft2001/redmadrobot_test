package com.dmdev.bootcamptest.data.dto;

import com.dmdev.bootcamptest.data.models.Role;
import com.dmdev.bootcamptest.data.models.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private String email;
    private String password;
    private boolean isActive;
    private String[] roles;

    @JsonIgnore
    public static UserDto getUserDtoFromModel(User model) {
        return new UserDto(
                model.getEmail(),
                model.getPassword(),
                model.isActive(),
                Arrays.copyOf(
                        model.getRoles().stream().map(Role::getName).toArray(),
                        model.getRoles().size(),
                        String[].class
                )
        );
    }
}
