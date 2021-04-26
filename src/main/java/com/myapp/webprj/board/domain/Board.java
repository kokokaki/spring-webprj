package com.myapp.webprj.board.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
import java.util.List;

@Setter @Getter
@ToString
public class Board {

    private Long bno;
    private String title;
    private String content;
    private String writer;
    private Date regDate;
    private Date updateDate;
    private int replyCnt;

    //첨부파일들의 이름목록을 저장할 필드
    private List<String> fileNames;
}
