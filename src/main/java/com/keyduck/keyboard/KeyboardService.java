package com.keyduck.keyboard;

import com.keyduck.keyboard.domain.Keyboard;
import com.keyduck.keyboard.dto.KeyboardCreateDto;
import com.keyduck.keyboard.repository.KeyboardRepository;
import com.keyduck.mapper.KeyboardMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KeyboardService {
    private final KeyboardRepository keyboardRepository;
    @Autowired
    KeyboardMapper keyboardMapper;

    public Keyboard addKeyboards(KeyboardCreateDto keyboard){
        Keyboard keyboardInfo = keyboardMapper.toEntity(keyboard);
        return keyboardRepository.save(keyboardInfo);
    }

}
