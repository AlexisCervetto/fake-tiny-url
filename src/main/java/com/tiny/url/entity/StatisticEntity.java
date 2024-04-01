package com.tiny.url.entity;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

@Data
@Builder
@RedisHash("StatisticEntity")
public class StatisticEntity {

  @Id
  private String id;

  @Indexed
  private String urlKey;

  private String os;

  private String browser;

  private String device;

  private LocalDateTime date;


}
