package com.keyduck.mapper;

import org.mapstruct.Mapper;

import com.keyduck.member.domain.Member;
import com.keyduck.member.dto.MemberGetDto;


@Mapper(componentModel = "spring")
public interface DataMapper extends GenericMapper<MemberGetDto,Member> {
	
}