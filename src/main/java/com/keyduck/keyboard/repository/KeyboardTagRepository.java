package com.keyduck.keyboard.repository;

import com.keyduck.keyboard.domain.KeyboardTags;
import com.keyduck.keyboard.domain.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KeyboardTagRepository  extends JpaRepository<KeyboardTags,Long> {
}
