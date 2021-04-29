package com.myapp.webprj.member.service;

import com.myapp.webprj.member.domain.Member;

public interface MemberService {

    //회원가입 처리
    void signUp(Member member);

    //데이터 중복체크
    /**
     * @param kind - 중복체크유형(ex: email, 계정명)
     * @param info - 사용자 입력데이터
     * @return 중복되면 true, 중복되지 않았으면 false
     */
    boolean isDuplicate(String kind, String info);

    //회원정보 조회처리
    Member getUser(String account);

    //로그인 처리
    String login(Member inputData, Member dbData);

}
