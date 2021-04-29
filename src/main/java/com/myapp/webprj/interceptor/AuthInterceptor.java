package com.myapp.webprj.interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

//인터셉터: 컨트롤러에 요청이 들어가기 전, 후에 공통처리할 일들을
//          만들어 놓는 클래스
@Configuration
public class AuthInterceptor implements HandlerInterceptor {

    //회원 인증이 필요한 페이지에 진입할 때 사전 인증검사를 수행하는
    //인터셉터의 사전처리 메서드
    // 리턴값이 true일 경우 컨트롤러 진입을 허용하고
    // false일 경우 진입을 허용하지 않음
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //세션객체를 얻는 방법
        //1. HttpSession타입을 활용하는 방법
        //2. HttpServletRequest타입에서 얻어오는 방법
        HttpSession session = request.getSession();

        //로그인을 안했을 경우
        if (session.getAttribute("loginUser") == null) {
            response.sendRedirect("/member/login");
            return false;
        }
        return true;
    }
}
