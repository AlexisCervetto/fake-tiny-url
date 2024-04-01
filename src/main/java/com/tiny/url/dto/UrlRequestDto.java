package com.tiny.url.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UrlRequestDto {

  @NotBlank
  private String url;

}
