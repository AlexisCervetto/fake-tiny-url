package com.tiny.url.repository;

import com.tiny.url.entity.UrlEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatisticsRepository extends CrudRepository<UrlEntity, String> {}
