package com.myapp.webprj.reply.service;

import com.myapp.webprj.board.mapper.BoardMapper;
import com.myapp.webprj.common.Criteria;
import com.myapp.webprj.reply.domain.Reply;
import com.myapp.webprj.reply.mapper.ReplyMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ReplyServiceImpl implements ReplyService {

    private final ReplyMapper replyMapper;
    private final BoardMapper boardMapper;

    @Override
    @Transactional
    public int register(Reply reply) {
        int count = replyMapper.insert(reply);
        boardMapper.increaseReplyCount(reply.getBno());
        return count;
    }

    @Override
    public Reply get(Long rno) {
        return replyMapper.read(rno);
    }

    @Override
    public int modify(Reply reply) {
        return replyMapper.update(reply);
    }

    @Override
    @Transactional
    public int remove(Long bno, Long rno) {
        int count = replyMapper.delete(rno);
        boardMapper.increaseReplyCount(bno);
        return count;
    }

    @Override
    public Map<String, Object> getList(Long bno, Criteria cri) {

        Map<String, Object> replyMap = new HashMap<>();
        replyMap.put("count", replyMapper.getCount(bno));
        replyMap.put("replies", replyMapper.getList(bno, cri));

        return replyMap;
    }
}
