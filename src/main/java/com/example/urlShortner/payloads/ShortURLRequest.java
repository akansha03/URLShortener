package com.example.urlShortner.payloads;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

import java.time.LocalDateTime;

@Data
public class ShortURLRequest {

    @NotBlank(message = "URL must not be blank")
    @Size(min = 5, message = "URL is too short")
    @URL(message = "Invalid URL Format")
    private String longUrl;

    private LocalDateTime expiresAt;
}
