package com.tiny.url.controller;

import com.tiny.url.bean.UrlService;
import com.tiny.url.dto.UrlRequestDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/url")
@RequiredArgsConstructor
public class UrlController {

  private final UrlService urlService;

  @PostMapping
  public ResponseEntity<String> createShortUrl(@RequestBody @NotNull UrlRequestDto requestDto) {
    return urlService.createShortUrl(requestDto);
  }

  @GetMapping("/long")
  public ResponseEntity<?> getLongUrl(@PathParam(value = "urlKey") @NotBlank String urlKey) {
    return urlService.getLongUrl(urlKey);
  }

  @GetMapping("/short")
  public ResponseEntity<?> getShortUrl(@PathParam(value = "url") @NotBlank String url) {
    return urlService.getShortUrl(url);
  }

  @GetMapping("/{urlKey}")
  public ResponseEntity<?> getAndRedirectLongUrl(@PathVariable @NotBlank String urlKey, @RequestHeader("User-Agent") String userAgentString) {
    return urlService.getAndRedirectLongUrl(urlKey, userAgentString);
  }

  @DeleteMapping("/{urlKey}")
  public ResponseEntity<String> deleteShortUrl(@PathVariable @NotBlank String urlKey) {
    return urlService.deleteShortUrl(urlKey);
  }

}
