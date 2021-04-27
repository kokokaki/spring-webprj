package com.myapp.webprj.member.mapper;

import com.myapp.webprj.member.domain.Auth;
import com.myapp.webprj.member.domain.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MemberMapperTest {

    @Autowired MemberMapper mapper;

    @Test
    @DisplayName("회원가입에 성공해야 한다.")
    void registerTest() {
        Member member = new Member();
        member.setAccount("abc4323");
        member.setPassword("aaa1234");
        member.setEmail("aaa3333@naver.com");
        member.setName("고길동");
        member.setAuth(Auth.COMMON);

        mapper.register(member);

        Member findUser = mapper.getUserInfo(member.getAccount());
        assertEquals(member.getAccount(), findUser.getAccount());
    }

    @Test
    @DisplayName("비밀번호가 인코딩된 상태로 회원가입에 성공해야 한다.")
    void registerTest2() {
        Member member = new Member();
        member.setAccount("apple");

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        member.setPassword(encoder.encode("apple123"));
        member.setEmail("apple123@naver.com");
        member.setName("도우너");
        member.setAuth(Auth.COMMON);

        mapper.register(member);
    }

    @Test
    @DisplayName("비밀번호가 암호화되어야 한다.")
    void encodePasswordTest() {

        //인코딩 전 비밀번호
        String rawPw = "ddd5555";

        //인코딩 후 비밀번호
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodedPw = encoder.encode(rawPw);

        System.out.println("============================");
        System.out.println("평문 비밀번호: " + rawPw);
        System.out.println("암호화 비밀번호: " + encodedPw);
    }

    @Test
    @DisplayName("로그인에 성공해야 한다.")
    void loginTest() {
        //로그인 시도 아이디
        String inputId = "apple";
        //로그인 시도 패스워드
        String inputPw = "apple123";

        //로그인 시도 아이디를 기반으로 회원정보를 조회한다.
        Member userInfo = mapper.getUserInfo(inputId);

        System.out.println("===========================================");
        if (userInfo != null) {
            System.out.println("userInfo = " + userInfo);

            //db에 저장된 비번
            String dbPw = userInfo.getPassword();

            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

            if (encoder.matches(inputPw, dbPw)) {
                System.out.println("로그인 성공!");
            } else {
                System.out.println("로그인 실패! 비밀번호 틀림!");
            }

        } else {
            System.out.println("회원가입을 먼저 진행하세요.");
        }
    }

}