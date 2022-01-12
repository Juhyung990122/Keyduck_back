package com.keyduck.keyboard.repository;

import com.keyduck.keyboard.domain.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository  extends JpaRepository<Tag,Long> {
    Tag findByContent(String content);
}
