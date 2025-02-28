package com.urlshortener.url_shortener.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.urlshortener.url_shortener.model.UrlEntity;

public interface UrlRepository extends JpaRepository<UrlEntity, Long> {
  Optional<UrlEntity> findByShortUrl(String shortUrl);
  Optional<UrlEntity> findByOriginalUrl(String originalUrl);
}
