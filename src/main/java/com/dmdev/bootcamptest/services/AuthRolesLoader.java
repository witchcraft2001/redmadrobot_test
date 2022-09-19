package com.dmdev.bootcamptest.services;

import com.dmdev.bootcamptest.data.models.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthRolesLoader implements CommandLineRunner {
    private final RoleService service;

    @Override
    public void run(String... args) {
        if (service.findAll().isEmpty()) {
            service.save(Role.builder().name("USER").build());
            service.save(Role.builder().name("ADMIN").build());
        }
    }
}
