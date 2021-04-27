package com.myapp.webprj.member.service;

import com.myapp.webprj.member.domain.Member;
import com.myapp.webprj.member.mapper.MemberMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberMapper memberMapper;

    @Override
    public void signUp(Member member) {

        //평문 비번
        String rawPassword = member.getPassword();
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        //암호화비번
        String encodedPassword = encoder.encode(rawPassword);
        member.setPassword(encodedPassword);

        memberMapper.register(member);
    }

    @Override
    public boolean isDuplicate(String kind, String info) {

        Map<String, Object> checkDataMap = new HashMap<>();
        checkDataMap.put("kind", kind);
        checkDataMap.put("info", info);

        //중복되면 1, 중복이 아니면 0
        int resultNumber = memberMapper.isDuplicate(checkDataMap);

        return resultNumber == 1;
    }

    @Override
    public Member getUser(String account) {
        return memberMapper.getUserInfo(account);
    }
}
