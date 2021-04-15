package com.myapp.webprj.reply.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Setter @Getter @ToString
public class Reply {

    private Long rno; //댓글 번호
    private Long bno; //원본게시글 번호

    private String reply;   //댓글 내용
    private String replyer; //댓글 작성자 이름
    private Date replyDate; //작성 시간
    private Date updateDate; //수정 시간
}
