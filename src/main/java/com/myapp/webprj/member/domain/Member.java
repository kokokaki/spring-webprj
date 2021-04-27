package com.myapp.webprj.member.domain;

import lombok.*;

import java.util.Date;

@Getter @Setter
@ToString @NoArgsConstructor
@AllArgsConstructor
public class Member {

    private String account;
    private String password;
    private String name;
    private String email;
    private Auth auth;
    private Date regDate;
}
