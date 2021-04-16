
CREATE SEQUENCE seq_reply;

CREATE TABLE tbl_reply (
    rno NUMBER(10),
    bno NUMBER(10) NOT NULL,
    reply VARCHAR2(1000) NOT NULL,
    replyer VARCHAR2(50) NOT NULL,
    reply_date DATE default SYSDATE,
    update_date DATE default SYSDATE
);

ALTER TABLE tbl_reply
ADD CONSTRAINT pk_reply
PRIMARY KEY (rno);

ALTER TABLE tbl_reply
ADD CONSTRAINT fk_reply_free_board
FOREIGN KEY (bno)
REFERENCES tbl_free_board (bno);

-- tbl_free_board 댓글 수 컬럼 추가
ALTER TABLE tbl_free_board
ADD reply_cnt NUMBER(10) DEFAULT 0;



