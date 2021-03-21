package com.keyduck.mapper;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GenericMapper<D, E> {
    D toDto(E entity);

    E toEntity(D dto);
}

