package com.tiny.url.bean;

import com.tiny.url.entity.StatisticEntity;
import com.tiny.url.entity.UrlEntity;
import com.tiny.url.repository.StatisticRepository;
import eu.bitwalker.useragentutils.UserAgent;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class StatisticService {

  private final StatisticRepository statisticRepo;

  @Transactional
  public void generateStatistic(UrlEntity urlEntity, String userAgent) {
    UserAgent userData = UserAgent.parseUserAgentString(userAgent);
    var statistic = StatisticEntity.builder().id(UUID.randomUUID().toString()) //
        .os(userData.getOperatingSystem().getName()) //
        .device(userData.getOperatingSystem().getDeviceType().getName()) //
        .browser(userData.getBrowser().getName())
        .date(LocalDateTime.now())
        .urlKey(urlEntity.getId()).build();
    statisticRepo.save(statistic);
  }

  public List<StatisticEntity> getStatistics(String urlKey) {
    return statisticRepo.findByUrlKey(urlKey);
  }
}
