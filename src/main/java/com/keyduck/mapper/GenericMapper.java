package com.keyduck.mapper;

import com.keyduck.keyboard.dto.SimpleKeyboardDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;


public interface GenericMapper<D, E> {
    D toDto(E entity);
    E toEntity(D dto);
}

