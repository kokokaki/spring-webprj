package com.myapp.webprj.reply.api;

import com.myapp.webprj.common.Criteria;
import com.myapp.webprj.reply.domain.Reply;
import com.myapp.webprj.reply.service.ReplyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Log4j2
@RequestMapping("/api/v1/replies")
@RequiredArgsConstructor
public class ReplyApiController {

    private final ReplyService replyService;

    //댓글 목록 조회
    //ResponseEntity : 클라이언트에 응답할때 단순 데이터만 넘기는게 아닌
    //                 각종 응답정보(상태코드, 응답헤더정보)를 함께 넘겨주는 객체
    @GetMapping("/{bno}/{page}")
    public ResponseEntity<List<Reply>> getList(
            @PathVariable Long bno,
            @PathVariable int page) {
        log.info("/api/v1/replies/" + bno + "/" + page + " GET!!");
        Criteria cri = new Criteria(page, 10);

        List<Reply> replies = replyService.getList(bno, cri);

        return new ResponseEntity<>(replies, HttpStatus.OK);
    }

    //댓글 개별 조회
    @GetMapping("/{rno}")
    public ResponseEntity<Reply> get(@PathVariable Long rno) {
        log.info("/api/v1/replies/" + rno + " : GET!!");
        return new ResponseEntity<>(replyService.get(rno), HttpStatus.OK);
    }

    //댓글 등록
    @PostMapping("/")
    public ResponseEntity<String> create(@RequestBody Reply reply) {
        log.info("/api/v1/replies/ POST : " + reply);

        int count = replyService.register(reply);

        return count == 1 ?
                new ResponseEntity<>("regSuccess", HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
