package com.myapp.webprj.reply.mapper;

import com.myapp.webprj.common.Criteria;
import com.myapp.webprj.reply.domain.Reply;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ReplyMapper {

    //댓글 입력
    int insert(Reply reply);

    //댓글 1개 조회
    Reply read(Long rno);

    //댓글 삭제
    int delete(Long rno);

    //댓글 수정
    int update(Reply reply);

    //총 댓글 수 조회
    int getCount(Long bno);

    //댓글 목록 조회
    /**
     * 마이바티스 매퍼는 일반적으로 1개의 파라미터만 받을 수
     * 있습니다. 그러나 @Param을 통해 여러 파라미터를 처리합니다.
     * @param bno
     * @param cri
     * @return
     */
    List<Reply> getList(
            @Param("bno") Long bno,
            @Param("cri") Criteria cri);
}
