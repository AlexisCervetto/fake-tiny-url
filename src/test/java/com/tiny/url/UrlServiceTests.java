package com.tiny.url;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.tiny.url.bean.StatisticService;
import com.tiny.url.bean.UrlService;
import com.tiny.url.dto.UrlRequestDto;
import com.tiny.url.entity.UrlEntity;
import com.tiny.url.repository.UrlRepository;
import java.util.Optional;
import org.apache.commons.validator.routines.UrlValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest
class UrlServiceTests {

  private static final String VALID_LONG_URL = "https://example.com";

  private static final String INVALID_LONG_URL = "invalid-url";

  private static final String VALID_URL_ENTITY_ID = "urlEntityId";

  private static final String INVALID_URL_ENTITY_ID = "nonExistentId";

  private static final String USER_AGENT = "User-Agent-String";

  @Mock
  private StatisticService statisticServiceMock;

  private UrlService urlService;

  private UrlValidator urlValidator;

  private UrlRepository urlRepo;

  @BeforeEach
  public void setUp() {
    urlValidator = mock(UrlValidator.class);
    urlRepo = mock(UrlRepository.class);
    urlService = new UrlService(urlRepo, statisticServiceMock);
  }

  @Test
  public void testCreateShortUrl_WithValidUrl() {
    when(urlValidator.isValid(anyString())).thenReturn(true);
    when(urlRepo.findById(anyString())).thenReturn(Optional.empty());

    ResponseEntity<String> response = urlService.createShortUrl(generateUrlRequest(VALID_LONG_URL));
    assertEquals(HttpStatus.CREATED, response.getStatusCode());
  }

  @Test
  public void testCreateShortUrl_WithInvalidUrl() {
    when(urlValidator.isValid(anyString())).thenReturn(false);

    ResponseEntity<String> response = urlService.createShortUrl(generateUrlRequest(INVALID_LONG_URL));
    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
  }

  @Test
  public void testCreateShortUrl_WithDuplicateUrl() {
    when(urlValidator.isValid(anyString())).thenReturn(true);
    when(urlRepo.findById(anyString())).thenReturn(Optional.of(mock(UrlEntity.class)));

    ResponseEntity<String> response = urlService.createShortUrl(generateUrlRequest(VALID_LONG_URL));
    assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
  }

  @Test
  public void testGetLongUrl_UrlExists() {
    UrlEntity urlEntity = mock(UrlEntity.class);
    urlEntity.setId(VALID_URL_ENTITY_ID);
    urlEntity.setLongUrl(VALID_LONG_URL);

    when(urlRepo.findById(anyString())).thenReturn(Optional.of(urlEntity));
    ResponseEntity<?> response = urlService.getAndRedirectLongUrl(VALID_URL_ENTITY_ID, USER_AGENT);

    verify(statisticServiceMock).generateStatistic(urlEntity, USER_AGENT);
    assertEquals(HttpStatus.FOUND, response.getStatusCode());
    assertTrue(response.getHeaders().containsKey(HttpHeaders.LOCATION));
    assertEquals(null, response.getHeaders().getFirst(HttpHeaders.LOCATION));
  }

  @Test
  public void testGetLongUrl_UrlNotExists() {
    when(urlRepo.findById(anyString())).thenReturn(Optional.empty());
    ResponseEntity<?> response = urlService.getAndRedirectLongUrl(INVALID_URL_ENTITY_ID, USER_AGENT);

    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    assertEquals("Short url key not exists.", response.getBody());
  }

  @Test
  public void testDeleteShortUrl_UrlExists() {
    UrlEntity urlEntity = mock(UrlEntity.class);
    urlEntity.setId(VALID_URL_ENTITY_ID);

    when(urlRepo.findById(anyString())).thenReturn(Optional.of(urlEntity));
    ResponseEntity<String> response = urlService.deleteShortUrl(VALID_URL_ENTITY_ID);
    verify(urlRepo).deleteById(VALID_URL_ENTITY_ID);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals("Short url deleted.", response.getBody());
  }

  @Test
  public void testDeleteShortUrl_UrlNotExists() {
    when(urlRepo.findById(anyString())).thenReturn(Optional.empty());
    ResponseEntity<String> response = urlService.deleteShortUrl(INVALID_URL_ENTITY_ID);

    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    assertEquals("Short url key not exists.", response.getBody());
    verify(urlRepo, never()).deleteById(anyString());
  }

  private UrlRequestDto generateUrlRequest(String url) {
    UrlRequestDto urlRequestDto = new UrlRequestDto();
    urlRequestDto.setUrl(url);
    return urlRequestDto;
  }

}
