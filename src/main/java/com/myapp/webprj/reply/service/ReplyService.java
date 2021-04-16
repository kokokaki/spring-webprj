package com.myapp.webprj.reply.service;

import com.myapp.webprj.common.Criteria;
import com.myapp.webprj.reply.domain.Reply;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

public interface ReplyService {

    //댓글 등록 서비스
    int register(Reply reply);

    //댓글 개별 조회 서비스
    Reply get(Long rno);

    //댓글 수정 서비스
    int modify(Reply reply);

    //댓글 삭제 서비스
    int remove(Long bno, Long rno);

    //댓글 목록조회 서비스
    Map<String, Object> getList(Long bno, Criteria cri);

}
