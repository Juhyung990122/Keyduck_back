package com.keyduck.keyboard.repository;

import com.keyduck.keyboard.domain.MainImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MainImageRepository extends JpaRepository<MainImage, Long> {
    List<MainImage> findAll();
}
