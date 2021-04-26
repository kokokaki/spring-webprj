
-- 첨부파일 정보를 가지는 테이블 생성
CREATE TABLE file_upload (
    file_name VARCHAR2(150),
    reg_date DATE DEFAULT SYSDATE,
    bno NUMBER(10) NOT NULL
);

-- PK, FK 부여
ALTER TABLE file_upload
ADD CONSTRAINT pk_file_name
PRIMARY KEY (file_name);

ALTER TABLE file_upload
ADD CONSTRAINT fk_file_upload_tbl_free_board
FOREIGN KEY (bno)
REFERENCES tbl_free_board (bno)
ON DELETE CASCADE;




