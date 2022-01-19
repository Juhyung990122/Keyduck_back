package com.keyduck.keyboard.repository;

import com.keyduck.keyboard.domain.Keyboard;
import com.keyduck.keyboard.domain.KeyboardTags;
import com.keyduck.keyboard.domain.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.security.Key;
import java.util.List;

public interface KeyboardTagRepository  extends JpaRepository<KeyboardTags,Long> {
    List<KeyboardTags> findFirst10ByTag(Tag tag);
}
