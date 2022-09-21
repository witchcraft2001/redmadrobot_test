package com.dmdev.bootcamptest.data.dto;

import com.dmdev.bootcamptest.data.constants.Status;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {
    private Status status;
    private int code;
    private Map<String, String> message;
    private T result;

    public static ApiResponse<String> getErrorResponse(int code, String message) {
        return new ApiResponse<>(Status.ERROR, code, Map.of("error", message), null);
    }
}
