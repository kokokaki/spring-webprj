package com.myapp.webprj.reply.mapper;

import com.myapp.webprj.common.Criteria;
import com.myapp.webprj.reply.domain.Reply;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ReplyMapperTest {

    @Autowired
    private ReplyMapper replyMapper;

    @Test
    @DisplayName("게시물 번호에 해당하는 20개의 댓글이 정상적으로 삽입되어야 한다.")
    void replyInsertTest() {
        for (int i = 1; i <= 20; i++) {

            Reply reply = new Reply();
            reply.setBno(1L);
            reply.setReply("1번게시물 댓글 no" + i);
            reply.setReplyer("테스터" + i);

            replyMapper.insert(reply);
        }
        assertTrue(replyMapper.getCount(1L) == 20);

    }

    @Test
    @DisplayName("특정 댓글을 조회할 수 있어야 한다.")
    void replyReadTest() {
        Reply reply = replyMapper.read(10L);
        System.out.println("===============================");
        System.out.println("reply = " + reply);

        assertEquals(reply.getBno(), 1L);
    }

    @Test
    @DisplayName("특정 댓글을 삭제할 수 있어야 한다.")
    @Transactional
    @Rollback
    void replyDeleteTest() {
        int delSuccessNum = replyMapper.delete(15L);

        assertTrue(delSuccessNum == 1);
        assertNull(replyMapper.read(15L));
    }

    @Test
    @DisplayName("특정 댓글을 수정할 수 있어야 한다.")
    void replyModifyTest() {
        Reply reply = replyMapper.read(6L);
        reply.setReply("댓글을 수정했지~");

        replyMapper.update(reply);

        Reply modifiedReply = replyMapper.read(6L);

        assertNotEquals(modifiedReply.getUpdateDate(), modifiedReply.getReplyDate());

    }

    @Test
    @DisplayName("댓글 목록을 페이징해서 조회할 수 있어야 한다.")
    void replyListTest() {

        Criteria cri = new Criteria(1, 10);
        List<Reply> replies = replyMapper.getList(1L, cri);

        for (Reply reply : replies) {
            System.out.println("reply = " + reply);
        }

        assertEquals(replies.size(), 10);
        assertEquals(replies.get(0).getRno(), replyMapper.read(1L).getRno());

    }


}