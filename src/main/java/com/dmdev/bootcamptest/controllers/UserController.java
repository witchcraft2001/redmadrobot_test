package com.dmdev.bootcamptest.controllers;

import com.dmdev.bootcamptest.data.dto.AuthTokenDto;
import com.dmdev.bootcamptest.data.dto.LoginUserDto;
import com.dmdev.bootcamptest.data.dto.UserDto;
import com.dmdev.bootcamptest.data.models.User;
import com.dmdev.bootcamptest.services.UserService;
import com.dmdev.bootcamptest.util.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider tokenProvider;
    private final UserService userService;

    @PostMapping(value = "/login")
    public ResponseEntity<?> login(@RequestBody LoginUserDto loginUser) throws AuthenticationException {
        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginUser.getEmail(),
                        loginUser.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        final String token = tokenProvider.generateJwtToken(authentication);
        return ResponseEntity.ok(new AuthTokenDto(token));
    }

    @PostMapping(value = "/register")
    public ResponseEntity<?> register(@RequestBody UserDto userDto) {
        User user = userService.save(userDto);
        user.setPassword("***");
        return ResponseEntity.ok(UserDto.getUserDtoFromModel(user));
    }

    @GetMapping(value = "/me")
    public ResponseEntity<?> getMe(HttpServletRequest request) throws Exception {
        Optional<User> result = userService.findByEmail(request.getUserPrincipal().getName());
        if (result.isPresent()) {
            User user = result.get();
            user.setPassword("***");
            return ResponseEntity.ok(UserDto.getUserDtoFromModel(user));
        }
        throw new Exception("Unknown user");
    }
}
