package com.urlshortener.url_shortener.services;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.urlshortener.url_shortener.exception.UrlNotFoundException;
import com.urlshortener.url_shortener.model.UrlEntity;
import com.urlshortener.url_shortener.repository.UrlRepository;

@Service
public class UrlServiceImpl implements UrlService {

  private final UrlRepository urlRepository;

  public UrlServiceImpl(UrlRepository urlRepository) {
    this.urlRepository = urlRepository;
  }

  // Validación de URLs
  private boolean isValidUrl(String url) {
    try {
      new URI(url);
      return true;
    } catch (URISyntaxException e) {
      return false;
    }
  }

  @Override
  public String shortenUrl(String originalUrl) {
    if (!isValidUrl(originalUrl)) {
      throw new IllegalArgumentException("Invalid URL");
    }
    // Verificar si la URL ya existe en la base de datos
    Optional<UrlEntity> existingUrl = urlRepository.findByOriginalUrl(originalUrl);
    if (existingUrl.isPresent()) {
      return existingUrl.get().getShortUrl();
    }

    // Generar una URL corta
    String shortUrl = generateShortUrl();

    // Guardar la nueva URL en la base de datos
    UrlEntity urlEntity = new UrlEntity();
    urlEntity.setOriginalUrl(originalUrl);
    urlEntity.setShortUrl(shortUrl);
    urlRepository.save(urlEntity);
    return shortUrl;
  }

  private String generateShortUrl() {
    return UUID.randomUUID().toString().replace("-", "").substring(0, 8);
  }

  @Override
  public String getOriginalUrl(String shortUrl) {
    return urlRepository.findByShortUrl(shortUrl)
        .map(UrlEntity::getOriginalUrl)
        .orElseThrow(() -> new UrlNotFoundException("Invalid short URL"));
  }
}
