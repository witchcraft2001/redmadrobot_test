package com.dmdev.bootcamptest.data.dto;

import com.dmdev.bootcamptest.data.models.Role;
import com.dmdev.bootcamptest.data.models.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Arrays;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto {
    private Long id;
    @Email(message = "Incorrect E-mail address")
    private String email;
    @Size(min = 6, max = 10, message = "Minimum password size 6, maximum 10")
    @NotBlank(message = "Password can't be empty")
    private String password;
    private boolean isActive;
    private String[] roles;

    @JsonIgnore
    public static UserDto getUserDtoFromModel(User model) {
        return new UserDto(
                model.getId(),
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
