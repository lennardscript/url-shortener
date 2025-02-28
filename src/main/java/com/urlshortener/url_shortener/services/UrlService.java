package com.urlshortener.url_shortener.services;

public interface UrlService {
  String shortenUrl(String originalUrl);
  String getOriginalUrl(String shortUrl);
}
