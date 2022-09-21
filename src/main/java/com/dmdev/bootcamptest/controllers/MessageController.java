package com.dmdev.bootcamptest.controllers;

import com.dmdev.bootcamptest.data.constants.Status;
import com.dmdev.bootcamptest.data.dto.*;
import com.dmdev.bootcamptest.data.mappers.MessageMapper;
import com.dmdev.bootcamptest.data.models.Message;
import com.dmdev.bootcamptest.services.MessageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@Api("Controller for messages management")
public class MessageController {
    private final MessageService service;
    private final MessageMapper mapper;

    @GetMapping("/api/bulletins/{id}/messages")
    @ApiOperation(value = "Gets a list of my messages by bulletinId")
    public ResponseEntity<?> list(@PathVariable(name = "id") long id, HttpServletRequest request) {
        Principal principal = request.getUserPrincipal();
        List<MessageDto> items = service.findAllMyMessagesByBulletinId(id, principal.getName())
                .stream().map(mapper::toDto).collect(Collectors.toList());
        return ResponseEntity.ok(new ApiResponse<>(Status.OK, HttpStatus.OK.value(), null, items));
    }

    @PostMapping("/api/bulletins/{id}/messages")
    @ApiOperation(value = "Add message to bulletin conversation")
    public ResponseEntity<?> add(@PathVariable(name = "id") long id, @Valid @RequestBody AddMessageDto dto, HttpServletRequest request) {
        Principal principal = request.getUserPrincipal();
        Message message = service.addMessageToBulletin(id, dto.getRecipientId(), principal.getName(), dto.getBody());

        return ResponseEntity.ok(new ApiResponse<>(Status.OK, HttpStatus.CREATED.value(), null, mapper.toDto(message)));
    }

    @DeleteMapping("/api/bulletins/{id}/messages/{message_id}")
    @ApiOperation(value = "Delete message from bulletin conversation")
    public ResponseEntity<?> delete(@PathVariable(name = "id") long id, @PathVariable(name = "message_id") long messageId, HttpServletRequest request) {
        Principal principal = request.getUserPrincipal();

        service.deleteById(messageId, principal.getName());
        return ResponseEntity.ok(new ApiResponse<>(Status.OK, HttpStatus.NO_CONTENT.value(), null, null));
    }
}
