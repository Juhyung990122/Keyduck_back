package com.keyduck.mapper;

import com.keyduck.member.domain.Member;
import com.keyduck.member.dto.MemberGetDto;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface MemberMapper extends GenericMapper<MemberGetDto,Member> {
	
}