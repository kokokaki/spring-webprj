package com.myapp.webprj.member.controller;

import com.myapp.webprj.member.domain.Member;
import com.myapp.webprj.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
@Log4j2
public class MemberController {

    private final MemberService memberService;

    //회원가입 페이지 화면 열기
    @GetMapping("/member/sign-up")
    public void signUp() {

    }

    //데이터(아이디, 이메일) 중복체크 비동기 요청처리
    // ~~~/check?kind=account&info=banana
    @GetMapping("/check")
    @ResponseBody
    public ResponseEntity<Boolean> check(String kind, String info) {

        boolean flag = memberService.isDuplicate(kind, info);
        return new ResponseEntity<>(flag, HttpStatus.OK);
    }

    //회원가입 요청 처리
    @PostMapping("/member/sign-up")
    public String signUp(Member member, RedirectAttributes ra) {
        log.info("/member/sign-up POST! : " + member);

        memberService.signUp(member);
        ra.addFlashAttribute("msg", "joinSuccess");
        return "redirect:/member/login";
    }

    //로그인 화면 요청 처리
    @GetMapping("/member/login")
    public void login() {}

    //로그인 수행 요청 처리
    @PostMapping("/loginCheck")
    public String loginCheck(Member inputUser, Model model, HttpSession session) {
        log.info("/loginCheck POST : " + inputUser);

        //회원가입이 되었는지 알아보기 위해 로그인 시도 아이디를 통해
        //DB에서 회원정보를 조회
        Member dbUser = memberService.getUser(inputUser.getAccount());

        //로그인 처리
        String result = memberService.login(inputUser, dbUser);
        log.info("로그인 처리 결과: " + result);

        model.addAttribute("result", result);

        if (result.equals("loginSuccess")) {
            //로그인 성공 처리
            //세션에 로그인한 유저정보를 기록
            session.setAttribute("loginUser", dbUser);
            return "redirect:/board/list";
        }
        return "member/login-result";
    }

    //로그아웃 요청 처리
    @GetMapping("/member/logout")
    public String logout(HttpSession session) {
        log.info("/member/logout GET ! ");

        Member loginUser = (Member) session.getAttribute("loginUser");

        if (loginUser != null) {
            //로그인을 한 사람은 세션을 무효화(전체 데이터 삭제)
            session.invalidate();
            return "redirect:/board/list";
        }
        return "redirect:/member/login";
    }

}
