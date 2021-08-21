package com.keyduck.mapper;

import com.keyduck.review.domain.Review;
import com.keyduck.review.dto.ReviewGetDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ReviewMapper extends GenericMapper<ReviewGetDto, Review>{
}
