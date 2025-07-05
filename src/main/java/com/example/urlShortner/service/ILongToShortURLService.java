package com.example.urlShortner.service;

import com.example.urlShortner.model.LongToShortURL;
import com.example.urlShortner.payloads.ShortURLRequest;

import java.util.Optional;

public interface ILongToShortURLService {

    Optional<LongToShortURL> shortenURL(ShortURLRequest shortURLRequest);
    LongToShortURL getOriginalUrl(String shortUrl);
    void deleteUrl(String longUrl);
}
