package com.tiny.url.controller;

import com.tiny.url.bean.StatisticService;
import com.tiny.url.entity.StatisticEntity;
import jakarta.validation.constraints.NotBlank;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/statistics")
@RequiredArgsConstructor
public class StatisticController {

  private final StatisticService statisticService;

  @GetMapping("/{urlKey}")
  public List<StatisticEntity> getStatistics(@PathVariable @NotBlank String urlKey) {
    return statisticService.getStatistics(urlKey);
  }

}
