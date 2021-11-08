package com.keyduck.mapper;

import com.keyduck.member.domain.Member;
import com.keyduck.member.dto.MemberTokenDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TokenMapper extends GenericMapper<MemberTokenDto, Member> {
    MemberTokenDto toDto(Member member, String accessToken, String refreshToken);
}
