package com.dmdev.bootcamptest.controllers;

import com.dmdev.bootcamptest.data.constants.Status;
import com.dmdev.bootcamptest.data.dto.ApiResponse;
import com.dmdev.bootcamptest.data.dto.TagDto;
import com.dmdev.bootcamptest.data.mappers.TagMapper;
import com.dmdev.bootcamptest.data.models.Tag;
import com.dmdev.bootcamptest.services.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/tags")
@RequiredArgsConstructor
public class TagController {
    private final TagService service;
    private final TagMapper mapper;

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> add(@RequestBody TagDto tagDto) {
        Tag tag = service.save(mapper.toModel(tagDto));
        return ResponseEntity.ok(new ApiResponse<>(Status.OK, HttpStatus.CREATED.value(), null, mapper.toDto(tag)));
    }

    @GetMapping
    public ResponseEntity<?> list() {
        List<TagDto> items = service.findAll().stream().map(mapper::toDto).collect(Collectors.toList());
        return ResponseEntity.ok(new ApiResponse<>(Status.OK, HttpStatus.OK.value(), null, items));
    }

    @PutMapping
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> update(@RequestBody TagDto tagDto) {
        Tag tag = service.update(mapper.toModel(tagDto));
        return ResponseEntity.ok(new ApiResponse<>(Status.OK, HttpStatus.ACCEPTED.value(), null, mapper.toDto(tag)));
    }
}
