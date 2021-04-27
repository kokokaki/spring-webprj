package com.myapp.webprj.member.service;

import com.myapp.webprj.member.domain.Auth;
import com.myapp.webprj.member.domain.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MemberServiceImplTest {

    @Autowired MemberService service;

    @Test
    @DisplayName("평문 패스워드가 포함된 회원정보를 주면 암호화해서 처리해야 한다.")
    void signUpTest() {
        Member member = new Member();
        member.setAccount("banana");
        member.setPassword("banana123");
        member.setName("김바나나");
        member.setEmail("banana@fruits.com");
        member.setAuth(Auth.COMMON);

        service.signUp(member);
    }

    @Test
    @DisplayName("아이디 중복을 확인해야 한다.")
    void accountDuplTest() {
        //중복검사할 아이디
        String targetAccount = "banana";

        boolean result = service.isDuplicate("account", targetAccount);

        assertTrue(result);
    }

    @Test
    @DisplayName("이메일 중복을 확인해야 한다.")
    void emailDuplTest() {
        //중복검사할 이메일
        String targetEmail = "banana@fruits.com";

        boolean result = service.isDuplicate("email", targetEmail);

        assertTrue(result);
    }

}









