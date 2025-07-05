package com.example.urlShortner.controller;

import com.example.urlShortner.model.LongToShortURL;
import com.example.urlShortner.payloads.ShortURLRequest;
import com.example.urlShortner.payloads.ShortURLResponse;
import com.example.urlShortner.service.LongToShortURLService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/shortURL")
public class LongToShortURLController {

    @Autowired
    private final LongToShortURLService longToShortURLService;

    @PostMapping("/")
    public ResponseEntity<ShortURLResponse> createShortURL(@Valid @RequestBody ShortURLRequest shortURLRequest) {
        /**
         * Author : Akansha Saraswat
         * Code to create a short URL from the long URL
         */
        Optional<LongToShortURL> longToShortURL = longToShortURLService.shortenURL(shortURLRequest);

        return longToShortURL
                .map(url -> ResponseEntity.ok(new ShortURLResponse(url.getShortUrl())))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
    }

    @GetMapping("/{shortCode}")
    public ResponseEntity<Void> redirectToOriginalUrl(@PathVariable String shortCode) {
        try {
            LongToShortURL longToShortURL = longToShortURLService.getOriginalUrl(shortCode);

            if(longToShortURL != null && longToShortURL.getLongUrl() != null) {
                return ResponseEntity.status(HttpStatus.FOUND)
                        .location(URI.create(longToShortURL.getLongUrl()))
                        .build();
            }
            else{
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
