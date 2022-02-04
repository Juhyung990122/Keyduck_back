package com.keyduck.keyboard.repository;

import com.keyduck.keyboard.domain.Keyboard;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import java.util.List;

public interface SearchRepository extends ElasticsearchRepository<Keyboard,Long> {
    List<Keyboard> findByInfoContains(String searchKeyword);
}
