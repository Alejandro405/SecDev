package com.example.secdev.utils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse {
    private String timestamp;
    private Integer status;
    private String error;
    private String message;
    private String path;

    public ErrorResponse(String s) {
        this.message = s;
        this.status = 400;
        this.timestamp = System.currentTimeMillis() + "";
    }
}
