package com.keyduck.scrap.repository;

import com.keyduck.scrap.domain.Scrap;
import com.keyduck.scrap.domain.ScrapId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScrapRepository extends JpaRepository<Scrap, ScrapId> {
}
