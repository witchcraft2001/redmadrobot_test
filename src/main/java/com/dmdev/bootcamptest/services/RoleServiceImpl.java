package com.dmdev.bootcamptest.services;

import com.dmdev.bootcamptest.data.models.Role;
import com.dmdev.bootcamptest.repositories.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository repository;

    @Override
    public Optional<Role> findByName(String name) {
        return repository.findRoleByName(name);
    }

    @Override
    public List<Role> findAll() {
        List<Role> list = new ArrayList<>();
        repository.findAll().iterator().forEachRemaining(list::add);
        return list;
    }

    @Override
    public Role save(Role role) {
        return repository.save(role);
    }
}
