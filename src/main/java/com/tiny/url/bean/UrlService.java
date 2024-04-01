package com.tiny.url.bean;

import com.google.common.hash.Hashing;
import com.tiny.url.dto.UrlRequestDto;
import com.tiny.url.entity.UrlEntity;
import com.tiny.url.repository.UrlRepository;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UrlService {

  @Value("${app.url.base}")
  private String urlBase;

  private final UrlRepository urlRepo;

  private final StatisticService statisticService;

  private final UrlValidator urlValidator = new UrlValidator();

  public ResponseEntity<String> createShortUrl(UrlRequestDto url) {
    var longUrl = url.getUrl();
    var id = String.valueOf(Hashing.murmur3_32().hashString(longUrl, StandardCharsets.UTF_8).asInt());

    if (!urlValidator.isValid(longUrl)) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid url: " + longUrl);
    }

    if (urlRepo.findById(id).isPresent()) {
      return ResponseEntity.status(HttpStatus.CONFLICT).body("Already exists short url for this url: " + longUrl);
    }

    return saveShortUrl(id, longUrl);
  }

  public ResponseEntity<?> getAndRedirectLongUrl(String urlKey, String userAgent) {
    var optUrlEntity = urlRepo.findById(urlKey);

    if (!optUrlEntity.isPresent()) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Short url key not exists.");
    }

    var urlEntity = optUrlEntity.get();
    statisticService.generateStatistic(urlEntity, userAgent);
    HttpHeaders headers = new HttpHeaders();
    headers.add(HttpHeaders.LOCATION, urlEntity.getLongUrl());
    return ResponseEntity.status(HttpStatus.FOUND).headers(headers).build();
  }

  public ResponseEntity<?> getLongUrl(String urlKey) {
    var optUrlEntity = urlRepo.findById(urlKey);

    if (!optUrlEntity.isPresent()) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Short url id not exists.");
    }

    var urlEntity = optUrlEntity.get();
    return ResponseEntity.status(HttpStatus.OK).body(urlEntity.getLongUrl());
  }

  public ResponseEntity<?> getShortUrl(String url) {
    var optUrlEntity = urlRepo.findByLongUrl(url);

    if (!optUrlEntity.isPresent()) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Long url id not exists.");
    }

    var urlEntity = optUrlEntity.get();
    return ResponseEntity.status(HttpStatus.OK).body(urlEntity.getShortUrl());
  }

  @Transactional
  public ResponseEntity<String> deleteShortUrl(String urlKey) {
    var optUrlEntity = urlRepo.findById(urlKey);
    if (!optUrlEntity.isPresent()) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Short url key not exists.");
    }
    urlRepo.deleteById(urlKey);
    return ResponseEntity.status(HttpStatus.OK).body("Short url deleted.");
  }

  @Transactional
  private ResponseEntity<String> saveShortUrl(String id, String longUrl) {
    var shortUrl = String.format("%s%s", urlBase, id);
    UrlEntity entity = UrlEntity.builder()
        .id(id)
        .shortUrl(shortUrl)
        .longUrl(longUrl)
        .createdAt(LocalDateTime.now()).build();
    urlRepo.save(entity);
    return ResponseEntity.status(HttpStatus.CREATED).body(shortUrl);
  }

}
