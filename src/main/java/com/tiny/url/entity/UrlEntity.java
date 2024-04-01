package com.tiny.url.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

@Data
@Builder
@RedisHash("UrlEntity")
public class UrlEntity implements Serializable {

  @Id
  private String id;

  private String shortUrl;

  @Indexed
  private String longUrl;

  private LocalDateTime createdAt;

}
