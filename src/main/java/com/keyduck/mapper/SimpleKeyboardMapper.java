package com.keyduck.mapper;


import com.keyduck.keyboard.domain.Keyboard;
import com.keyduck.keyboard.dto.SimpleKeyboardDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SimpleKeyboardMapper extends GenericMapper<SimpleKeyboardDto, Keyboard>{
    SimpleKeyboardDto toDto(Keyboard keyboard);
}
