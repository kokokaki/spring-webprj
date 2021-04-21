package com.myapp.webprj.common;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Controller
@Log4j2
public class UploadController {

    //업로드 form jsp파일을 포워딩하는 처리
    @GetMapping("/uploadForm")
    public String uploadForm() {
        return "upload/upload-form";
    }

    //업로드된 파일을 처리
    //MultipartFile : 클라이언트가 전송한 파일데이터
    @PostMapping("/upload")
    public String upload(@RequestParam("file") List<MultipartFile> fileList) {

        //업로드파일 저장 경로
        String uploadPath = "D:\\developing_hsg\\upload";

        for (MultipartFile file : fileList) {
            log.info("file-name: " + file.getOriginalFilename());
            log.info("size: " + file.getSize());
            log.info("file-type: " + file.getContentType());
            System.out.println("===============================================================");

            //세이브파일 객체 생성
            //생성자의 첫번째 파라미터로 저장경로를, 두번째 파라미터로 파일명을 넣어주세요
            File saveFile = new File(uploadPath, file.getOriginalFilename());

            try {
                //서버에 파일을 저장하는 메서드
                file.transferTo(saveFile);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return "";
    }

}
