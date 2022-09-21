package com.dmdev.bootcamptest.data.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddMessageDto {
    @NotBlank(message = "Message body can't be empty")
    private String body;
    private long recipientId;
}
