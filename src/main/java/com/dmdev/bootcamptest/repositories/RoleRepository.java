package com.dmdev.bootcamptest.repositories;

import com.dmdev.bootcamptest.data.models.Role;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RoleRepository extends CrudRepository<Role, Long> {
    Optional<Role> findRoleByName(String name);
}
