package com.example.urlShortner.repository;

import com.example.urlShortner.model.LongToShortURL;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface LongToShortURLRepository extends JpaRepository<LongToShortURL, Long> {

     Optional<LongToShortURL> findByShortCode(String shortCode);

     Optional<LongToShortURL> findByLongUrl(String longUrl);

     boolean existsByShortUrl(String shortUrl);

     boolean existsByLongUrl(String longUrl);

     void deleteByLongUrl(String longUrl);

}
