package com.keyduck.member.dto;

import java.util.List;


import org.springframework.web.multipart.MultipartFile;




public class MemberUpdateDto {
	private  List<MultipartFile> profile;
	
	public List<MultipartFile> getProfile() {
		return this.profile;
	}
	
	public void setProfile(List<MultipartFile> profile) {
		this.profile = profile;
	}
}
