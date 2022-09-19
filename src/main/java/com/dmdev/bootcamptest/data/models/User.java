package com.dmdev.bootcamptest.data.models;

import com.dmdev.bootcamptest.listeners.UserListener;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

import static com.dmdev.bootcamptest.data.constants.SecurityConstants.AUTHORITIES;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "USERS")
@EntityListeners(UserListener.class)
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false)
    private Long id;

    @Column(unique = true)
    private String email;

    @Column
    private String password;

    @Column
    private boolean isActive = true;

    @DateTimeFormat
    private Date createdAt = Date.from(Instant.now());

    @OneToMany(mappedBy = "author", targetEntity = Bulletin.class, cascade = CascadeType.ALL)
    private Collection<Bulletin> bulletins;

    @OneToMany(mappedBy = "recipient", targetEntity = Message.class, cascade = CascadeType.ALL)
    private Collection<Message> receivedMessages;

    @OneToMany(mappedBy = "sender", targetEntity = Message.class, cascade = CascadeType.ALL)
    private Collection<Message> sentMessages;

    @OneToMany(mappedBy = "author", targetEntity = Image.class, cascade = CascadeType.ALL)
    private Collection<Image> loadedImages;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "USER_ROLES",
            joinColumns = {@JoinColumn(name = "USER_ID")},
            inverseJoinColumns = {@JoinColumn(name = "ROLE_ID")}
    )
    private Set<Role> roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
