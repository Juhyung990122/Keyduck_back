package com.keyduck.keyboard.repository;

import com.keyduck.keyboard.domain.Keyboard;
import com.keyduck.keyboard.dto.KeyboardGetDto;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import java.security.Key;
import java.util.List;
import java.util.Optional;

public interface KeyboardRepository extends JpaRepository<Keyboard, Long>, JpaSpecificationExecutor<Keyboard>{
    List<Keyboard> findAll(@Nullable Specification<Keyboard> spec);
    List<Keyboard> findTop10ByOrderByDateDesc();
}
