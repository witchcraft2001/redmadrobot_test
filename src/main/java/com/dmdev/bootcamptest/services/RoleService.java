package com.dmdev.bootcamptest.services;

import com.dmdev.bootcamptest.data.models.Role;

import java.util.List;
import java.util.Optional;

public interface RoleService {
    Optional<Role> findByName(String name);
    List<Role> findAll();
    Role save(Role role);
}
