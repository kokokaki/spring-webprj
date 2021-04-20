package com.myapp.webprj.reply.api;

import com.myapp.webprj.common.Criteria;
import com.myapp.webprj.reply.domain.Reply;
import com.myapp.webprj.reply.service.ReplyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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
    public ResponseEntity<Map<String, Object>> getList(
            @PathVariable Long bno,
            @PathVariable int page) {
        log.info("/api/v1/replies/" + bno + "/" + page + " GET!!");
        Criteria cri = new Criteria(page, 10);

        Map<String, Object> replies = replyService.getList(bno, cri);

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

    //댓글 수정
    @RequestMapping(value = "/{rno}", method = {RequestMethod.PUT, RequestMethod.PATCH})
    public ResponseEntity<String> modify(
            @PathVariable Long rno,
            @RequestBody Reply reply) {

        reply.setRno(rno);
        log.info("/api/v1/replies/" + rno + " PUT: " + reply);

        int modCount = replyService.modify(reply);
        return modCount == 1
                ? new ResponseEntity<>("modSuccess", HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    //댓글 삭제 요청 처리
    @DeleteMapping("/{bno}/{rno}")
    public ResponseEntity<String> remove(@PathVariable Long bno, @PathVariable Long rno) {

        log.info("/api/v1/replies/" + bno + "/" + rno + " DELETE");

        return replyService.remove(bno, rno) == 1
                ? new ResponseEntity<>("delSuccess", HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
