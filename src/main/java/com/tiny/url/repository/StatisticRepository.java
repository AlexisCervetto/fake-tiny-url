package com.tiny.url.repository;

import com.tiny.url.entity.StatisticEntity;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatisticRepository extends CrudRepository<StatisticEntity, String> {

  List<StatisticEntity> findByUrlKey(String urlKey);

}
