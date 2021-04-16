package com.keyduck.keyboard.repository;

import com.keyduck.keyboard.domain.Keyboard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KeyboardRepository extends JpaRepository<Keyboard, Long> {

}
