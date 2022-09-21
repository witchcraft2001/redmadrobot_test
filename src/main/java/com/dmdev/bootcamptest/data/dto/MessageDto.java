package com.dmdev.bootcamptest.data.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MessageDto {
    private Long id;
    private String body;
    private long bulletinId;
    private long senderId;
    private String sender;
    private long recipientId;
    private String recipient;
    private Date createdAt;
}
