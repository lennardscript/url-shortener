package com.urlshortener.url_shortener.controller;

import org.springframework.http.HttpHeaders;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.urlshortener.url_shortener.controller.dto.UrlRequestDTO;
import com.urlshortener.url_shortener.services.UrlService;

@RestController
@RequestMapping("api/urls")
public class UrlController {

  private final UrlService urlService;

  public UrlController(UrlService urlService) {
    this.urlService = urlService;
  }

  @PostMapping("/shorten")
  public ResponseEntity<String> shortenUrl(@RequestBody UrlRequestDTO request) {
    String originalUrl = request.getUrl();
    String shortUrl = urlService.shortenUrl(originalUrl);
    return ResponseEntity.ok(shortUrl);
  }

  @GetMapping("/{shortUrl}")
  public ResponseEntity<Void> redirectToOriginalUrl(@PathVariable String shortUrl) {
    String originalUrl = urlService.getOriginalUrl(shortUrl);
    return ResponseEntity.status(HttpStatus.FOUND)
                          .header(HttpHeaders.LOCATION, originalUrl)
                          .build();
  }
}
