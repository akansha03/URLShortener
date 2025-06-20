package com.example.urlShortner.controller;

import com.example.urlShortner.model.LongToShortURL;
import com.example.urlShortner.request.ShortURLRequest;
import com.example.urlShortner.request.ShortURLResponse;
import com.example.urlShortner.service.LongToShortURLService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/shortURL")
public class LongToShortURLController {

    private final LongToShortURLService longToShortURLService;

    @PostMapping("/")
    public ResponseEntity<ShortURLResponse> createShortURL(@RequestBody ShortURLRequest shortURLRequest) {
        /**
         * Author : Akansha Saraswat
         * Code to
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
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
