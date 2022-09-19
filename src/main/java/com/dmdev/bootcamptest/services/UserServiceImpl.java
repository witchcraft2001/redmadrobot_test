package com.dmdev.bootcamptest.services;

import com.dmdev.bootcamptest.data.dto.UserDto;
import com.dmdev.bootcamptest.data.mappers.UserMapper;
import com.dmdev.bootcamptest.data.models.Role;
import com.dmdev.bootcamptest.data.models.User;
import com.dmdev.bootcamptest.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.*;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private static final String USER_ROLE = "USER";
    private final RoleService roleService;
    private final UserRepository repository;
    private final UserMapper userMapper;

    @Override
    public User save(UserDto user) {
        User model = userMapper.toModel(user);
        model.setActive(true);
        model.setCreatedAt(Date.from(Instant.now()));
        Set<Role> roles = new HashSet<>();

        if (user.getRoles() != null && user.getRoles().length > 0) {
            Arrays.stream(user.getRoles()).forEach(
                    name -> {
                        Optional<Role> role = roleService.findByName(name);
                        role.ifPresent(roles::add);
                    }
            );
        }
        if (roles.isEmpty()) {
            Optional<Role> role = roleService.findByName(USER_ROLE);
            role.ifPresent(roles::add);
        }

        model.setRoles(roles);

        return repository.save(model);
    }

    @Override
    public List<User> findAll() {
        List<User> list = new ArrayList<>();
        repository.findAll().iterator().forEachRemaining(list::add);
        return list;
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return repository.findByEmail(email);
    }
}
