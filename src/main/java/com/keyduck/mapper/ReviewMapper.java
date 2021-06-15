package com.keyduck.mapper;

import com.keyduck.review.domain.Review;
import com.keyduck.review.dto.ReviewGetDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ReviewMapper extends GenericMapper<ReviewGetDto, Review>{
}
