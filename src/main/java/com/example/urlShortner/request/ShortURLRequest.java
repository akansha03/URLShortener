package com.example.urlShortner.request;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ShortURLRequest {

    private String longUrl;
    private LocalDateTime expiresAt;
}
