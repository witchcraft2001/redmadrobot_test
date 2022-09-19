package com.dmdev.bootcamptest.listeners;

import com.dmdev.bootcamptest.data.models.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

public class UserListener {
    @PrePersist
    @PreUpdate
    void hashPassword(User user) {
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
    }
}
