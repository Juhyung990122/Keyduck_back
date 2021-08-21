package com.keyduck.mapper;

import com.keyduck.member.domain.Member;
import com.keyduck.member.dto.MemberGetDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MemberMapper extends GenericMapper<MemberGetDto,Member> {
    MemberGetDto toDto(Member member, String token);
}