package com.keyduck.keyboard.repository;
import com.keyduck.keyboard.domain.Keyboard;
import com.keyduck.utils.ElasticSearch.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SearchRepository extends ElasticsearchRepository {
    List<Keyboard> findByInfoContains(String searchKeyword);
}
