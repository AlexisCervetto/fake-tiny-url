package com.tiny.url.repository;

import com.tiny.url.entity.UrlEntity;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UrlRepository extends CrudRepository<UrlEntity, String> {

  Optional<UrlEntity> findByLongUrl(String longUrl);

}
