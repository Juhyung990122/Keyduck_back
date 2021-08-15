package com.keyduck.mapper;

import com.keyduck.keyboard.domain.Keyboard;
import com.keyduck.keyboard.dto.KeyboardGetDto;
import com.keyduck.keyboard.dto.SimpleKeyboardDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface KeyboardMapper extends GenericMapper<KeyboardGetDto, Keyboard>{
}
