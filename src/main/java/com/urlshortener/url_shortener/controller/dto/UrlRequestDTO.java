package com.urlshortener.url_shortener.controller.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UrlRequestDTO {

  @NotBlank(message = "URL cannot be empty")
  private String url;

}
