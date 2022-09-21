package com.dmdev.bootcamptest.controllers;

import com.dmdev.bootcamptest.data.constants.Status;
import com.dmdev.bootcamptest.data.dto.ApiResponse;
import com.dmdev.bootcamptest.data.dto.BulletinDto;
import com.dmdev.bootcamptest.data.mappers.BulletinMapper;
import com.dmdev.bootcamptest.data.models.Bulletin;
import com.dmdev.bootcamptest.services.BulletinService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/bulletins")
@RequiredArgsConstructor
@Api("Controller for bulletins management")
public class BulletinController {
    private final BulletinService service;
    private final BulletinMapper mapper;

    @PostMapping
    @ApiOperation(value = "Add a new bulletin")
    public ResponseEntity<?> add(@RequestBody BulletinDto dto, HttpServletRequest request) {
        Principal principal = request.getUserPrincipal();
        Bulletin model = service.save(dto, principal.getName());
        return ResponseEntity.ok(new ApiResponse<>(Status.OK, HttpStatus.CREATED.value(), null, mapper.toDto(model)));
    }

    @GetMapping
    @ApiOperation(value = "Gets a list of bulletins")
    public ResponseEntity<?> list(HttpServletRequest request) {
        Principal principal = request.getUserPrincipal();
        List<BulletinDto> items = service.getPublished().stream().map(mapper::toDto).collect(Collectors.toList());
        return ResponseEntity.ok(new ApiResponse<>(Status.OK, HttpStatus.CREATED.value(), null, items));
    }

    @GetMapping("/all")
    @ApiOperation(value = "Gets a list of all bulletins, included unpublished and inactive")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> getAll(HttpServletRequest request) {
        List<BulletinDto> items = service.findAll().stream().map(mapper::toDto).collect(Collectors.toList());
        return ResponseEntity.ok(new ApiResponse<>(Status.OK, HttpStatus.CREATED.value(), null, items));
    }

    @GetMapping("/my")
    @ApiOperation(value = "Gets a list of bulletins for logged user")
    public ResponseEntity<?> getMy(HttpServletRequest request) {
        Principal principal = request.getUserPrincipal();
        List<BulletinDto> items = service.findMy(principal.getName()).stream().map(mapper::toDto).collect(Collectors.toList());
        return ResponseEntity.ok(new ApiResponse<>(Status.OK, HttpStatus.CREATED.value(), null, items));
    }

    @PutMapping(path = "/{id}/publish")
    @ApiOperation(value = "Publish bulletin on a board")
    public ResponseEntity<?> publish(@PathVariable(name = "id") long id, HttpServletRequest request) {
        Principal principal = request.getUserPrincipal();
        Bulletin model = service.updatePublishedBulletin(id, principal.getName(), true);
        return ResponseEntity.ok(new ApiResponse<>(Status.OK, HttpStatus.CREATED.value(), null, mapper.toDto(model)));
    }

    @PutMapping(path = "/{id}/unpublish")
    @ApiOperation(value = "Unpublish bulletin")
    public ResponseEntity<?> unpublish(@PathVariable(name = "id") long id, HttpServletRequest request) {
        Principal principal = request.getUserPrincipal();
        Bulletin model = service.updatePublishedBulletin(id, principal.getName(), false);
        return ResponseEntity.ok(new ApiResponse<>(Status.OK, HttpStatus.CREATED.value(), null, mapper.toDto(model)));
    }

    @PutMapping(path = "/{id}/activate")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @ApiOperation(value = "Activate bulletin (Only for admins)")
    public ResponseEntity<?> activate(@PathVariable(name = "id") long id, HttpServletRequest request) {
        Bulletin model = service.updateActiveBulletin(id, true);
        return ResponseEntity.ok(new ApiResponse<>(Status.OK, HttpStatus.CREATED.value(), null, mapper.toDto(model)));
    }

    @PutMapping(path = "/{id}/deactivate")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @ApiOperation(value = "Deactivate bulletin (Only for admins)")
    public ResponseEntity<?> deactivate(@PathVariable(name = "id") long id, HttpServletRequest request) {
        Bulletin model = service.updateActiveBulletin(id, false);
        return ResponseEntity.ok(new ApiResponse<>(Status.OK, HttpStatus.CREATED.value(), null, mapper.toDto(model)));
    }
}
