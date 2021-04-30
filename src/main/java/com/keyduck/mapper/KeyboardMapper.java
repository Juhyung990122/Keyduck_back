package com.keyduck.mapper;

import com.keyduck.keyboard.domain.Keyboard;
import com.keyduck.keyboard.dto.KeyboardCreateDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface KeyboardMapper extends GenericMapper<KeyboardCreateDto, Keyboard>{
}
