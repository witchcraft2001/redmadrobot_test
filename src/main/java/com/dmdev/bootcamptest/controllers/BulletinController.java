package com.dmdev.bootcamptest.controllers;

import com.dmdev.bootcamptest.data.constants.Status;
import com.dmdev.bootcamptest.data.dto.ApiResponse;
import com.dmdev.bootcamptest.data.dto.BulletinDto;
import com.dmdev.bootcamptest.data.mappers.BulletinMapper;
import com.dmdev.bootcamptest.data.models.Bulletin;
import com.dmdev.bootcamptest.data.models.User;
import com.dmdev.bootcamptest.repositories.UserRepository;
import com.dmdev.bootcamptest.services.BulletinService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/bulletins")
@RequiredArgsConstructor
public class BulletinController {
    private final BulletinService service;
    private final UserRepository userRepository;
    private final BulletinMapper mapper;

    @PostMapping
    public ResponseEntity<?> add(@RequestBody BulletinDto dto, HttpServletRequest request) {
        Principal principal = request.getUserPrincipal();
        Bulletin model = service.save(dto, principal.getName());
        return ResponseEntity.ok(new ApiResponse<>(Status.OK, HttpStatus.CREATED.value(), null, mapper.toDto(model)));
    }

    @GetMapping
    public ResponseEntity<?> list(HttpServletRequest request) {
//        Principal principal = request.getUserPrincipal();
//        Optional<User> user = userRepository.findByEmail(principal.getName());

        List<BulletinDto> items = service.findAll(true).stream().map(mapper::toDto).collect(Collectors.toList());
        return ResponseEntity.ok(new ApiResponse<>(Status.OK, HttpStatus.CREATED.value(), null, items));
    }
}
