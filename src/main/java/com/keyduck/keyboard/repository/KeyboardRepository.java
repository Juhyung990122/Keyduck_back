package com.keyduck.keyboard.repository;

import com.keyduck.keyboard.domain.Keyboard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface KeyboardRepository extends JpaRepository<Keyboard, Long> {
    Optional<Keyboard> findByKeyId(String model);

}
