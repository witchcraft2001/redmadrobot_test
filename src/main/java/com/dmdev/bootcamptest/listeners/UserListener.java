package com.dmdev.bootcamptest.listeners;

import com.dmdev.bootcamptest.data.models.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

@Component
public class UserListener {
    private BCryptPasswordEncoder encoder;

    @PrePersist
    @PreUpdate
    void hashPassword(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
    }
}
