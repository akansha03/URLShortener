package com.example.urlShortner.service;

import com.example.urlShortner.constants.URLConstants;
import com.example.urlShortner.exceptions.ResourceNotFoundException;
import com.example.urlShortner.model.LongToShortURL;
import com.example.urlShortner.repository.LongToShortURLRepository;
import com.example.urlShortner.payloads.ShortURLRequest;
import com.example.urlShortner.utilities.Base62Encoder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.CachePut;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LongToShortURLService implements ILongToShortURLService{

    private final LongToShortURLRepository repository;

    @Override
    @CachePut(value = "shortUrlCache", key = "#result.shortCode", unless = "#result == null")
    public Optional<LongToShortURL> shortenURL(ShortURLRequest shortURLRequest) {

        return repository.findByLongUrl(shortURLRequest.getLongUrl())
                .map(Optional::of)
                .orElseGet(() -> {
                    LongToShortURL entity = createBaseEntity(shortURLRequest);
                    entity = repository.save(entity);

                    String shortCode = generateShortCode(entity.getId());
                    entity.setShortCode(shortCode);
                    entity.setShortUrl(URLConstants.BASE_URL + shortCode);
                    entity = repository.save(entity);
                    return Optional.of(entity);
                });
    }

    private LongToShortURL createBaseEntity(ShortURLRequest shortURLRequest) {
        LongToShortURL entity = new LongToShortURL();
        entity.setLongUrl(shortURLRequest.getLongUrl());
        entity.setExpiresAt(LocalDateTime.now());
        entity.setExpiresAt(shortURLRequest.getExpiresAt());
        return entity;
    }

    private String generateShortCode(Long id) {
        long offset = 100_00L;
        return Base62Encoder.encode(offset + id);
    }

    private boolean longURLExists(String longURL) {
        return repository.existsByLongUrl(longURL);
    }

    private boolean shortURLExists(String shortURL) {
        return repository.existsByShortUrl(shortURL);
    }

    @Override
    @Cacheable(value = "shortUrlCache", key = "#shortCode", unless = "#result == null")
    public LongToShortURL getOriginalUrl(String shortCode) {
        return repository.findByShortCode(shortCode)
                .orElseThrow(() -> new ResourceNotFoundException("Original URL Not found"));
    }

    @Override
    public void deleteUrl(String longUrl) {
        repository.findByLongUrl(longUrl)
                .filter(this::isExpired)
                .ifPresent(repository::delete);
    }

    private boolean isExpired(LongToShortURL longToShortURL) {
        return longToShortURL.getExpiresAt() != null && LocalDateTime.now().isAfter(longToShortURL.getExpiresAt());
    }
}
