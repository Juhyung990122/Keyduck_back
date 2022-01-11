package com.keyduck.member.dto;
import com.keyduck.member.domain.Member;
import lombok.*;
import org.hibernate.annotations.common.reflection.XMember;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(builderMethodName = "MemberTokenBuilder")
public class MemberTokenDto {
    private Long memId;
    private String access_token;
    private String refresh_token;


    public static MemberTokenDto toDto(Member member) {
        MemberTokenDto memberTokenDto = new MemberTokenDto();
        memberTokenDto.memId = member.getMemId();
        memberTokenDto.access_token = member.getAccessToken();
        memberTokenDto.refresh_token = member.getRefreshToken();

        return memberTokenDto;
    }
}
