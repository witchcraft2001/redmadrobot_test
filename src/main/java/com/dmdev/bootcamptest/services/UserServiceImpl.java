package com.dmdev.bootcamptest.services;

import com.dmdev.bootcamptest.data.dto.UserDto;
import com.dmdev.bootcamptest.data.mappers.UserMapper;
import com.dmdev.bootcamptest.data.models.Role;
import com.dmdev.bootcamptest.data.models.User;
import com.dmdev.bootcamptest.exceptions.UserEmailUnavailableException;
import com.dmdev.bootcamptest.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.*;

import static com.dmdev.bootcamptest.data.constants.SecurityConstants.USER_ROLE_NAME;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final RoleService roleService;
    private final UserRepository repository;
    private final UserMapper userMapper;

    @Override
    public User save(UserDto user) {
        if (!user.getEmail().isBlank() && !emailExists(user.getEmail())) {
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
                Optional<Role> role = roleService.findByName(USER_ROLE_NAME);
                role.ifPresent(roles::add);
            }

            model.setRoles(roles);

            return repository.save(model);
        }
        throw new UserEmailUnavailableException("The user E-Mail '${userDetails.email}' is unavailable.");
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

    @Override
    public Optional<User> findById(long id) {
        return repository.findById(id);
    }

    private boolean emailExists(String email) {
        return repository.findByEmail(email).isPresent();
    }
}
