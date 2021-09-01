package com.keyduck.mapper;

import com.keyduck.keyboard.domain.Keyboard;
import com.keyduck.keyboard.dto.KeyboardGetDto;
import com.keyduck.keyboard.dto.SimpleKeyboardDto;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface KeyboardMapper extends GenericMapper<KeyboardGetDto, Keyboard>{
    KeyboardGetDto keyboardsToGet(Keyboard keyboard);
}