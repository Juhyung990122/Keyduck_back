package com.keyduck.mapper;

import com.keyduck.member.domain.Member;
import com.keyduck.member.dto.LoginMemberDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface LoginMapper extends GenericMapper<LoginMemberDto, Member> {
    LoginMemberDto toDto(Member member,String accessToken,String refreshToken);
}