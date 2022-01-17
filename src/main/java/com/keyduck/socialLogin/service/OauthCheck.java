package com.keyduck.socialLogin.service;

import com.keyduck.member.domain.Member;
import com.keyduck.member.domain.MemberRole;
import com.keyduck.member.domain.MemberType;
import com.keyduck.member.dto.MemberTokenDto;
import com.keyduck.member.repository.MemberRepository;
import com.keyduck.security.JwtTokenProvider;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class OauthCheck {

    private MemberRepository memberRepository;
    private JwtTokenProvider jwtTokenProvider;

    public OauthCheck(MemberRepository memberRepository, JwtTokenProvider jwtTokenProvider){
        this.memberRepository = memberRepository;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public Member checkMemberExist(String socialId, String socialLoginType, String email, String nickname){
        Optional<Member> findMember= memberRepository.findBySocialIdAndType(socialId, MemberType.getAuthType(socialLoginType));
        Member member;
        //있으면 사용자 정보 get
        //없으면 필요한 정보 저장(socialId,type,email)
        if(findMember.isPresent()){
            member = findMember.get();
        }else {
            member = Member.MemberBuilder()
                    .socialId(socialId)
                    .email(email)
                    .nickname(nickname)
                    .type(MemberType.getAuthType(socialLoginType))
                    .role(MemberRole.USER)
                    .build();
            memberRepository.save(member);
        }
        return member;
    }

    public MemberTokenDto createToken(Member member){
        //token 생성
        String keyduckAccessToken = jwtTokenProvider.createToken(member.getUsername(), member.getRole());
        String keyduckRefreshToken = jwtTokenProvider.createRefreshToken(member.getUsername(),member.getRole());
        member.setAccessToken(keyduckAccessToken);
        member.setRefreshToken(keyduckRefreshToken);
        memberRepository.save(member);

        return MemberTokenDto.MemberTokenBuilder()
                .memId(member.getMemId())
                .access_token(keyduckAccessToken)
                .refresh_token(keyduckRefreshToken)
                .build();
    }
}
