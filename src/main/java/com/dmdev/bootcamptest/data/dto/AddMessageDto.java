package com.dmdev.bootcamptest.data.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddMessageDto {
    private String body;
    private long recipientId;
}
