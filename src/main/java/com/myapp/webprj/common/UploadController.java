package com.myapp.webprj.common;

import com.myapp.webprj.util.FileUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Controller
@Log4j2
public class UploadController {

    //업로드파일 저장 경로
    private static final String uploadPath = "D:\\developing_hsg\\upload";

    //업로드 form jsp파일을 포워딩하는 처리
    @GetMapping("/uploadForm")
    public String uploadForm() {
        return "upload/upload-form";
    }

    //업로드된 파일을 처리
    //MultipartFile : 클라이언트가 전송한 파일데이터
    @PostMapping("/upload")
    public String upload(@RequestParam("file") List<MultipartFile> fileList) {


        for (MultipartFile file : fileList) {
            log.info("file-name: " + file.getOriginalFilename());
            log.info("size: " + file.getSize());
            log.info("file-type: " + file.getContentType());
            System.out.println("===============================================================");

            //세이브파일 객체 생성
            //생성자의 첫번째 파라미터로 저장경로를, 두번째 파라미터로 파일명을 넣어주세요
//            File saveFile = new File(uploadPath, file.getOriginalFilename());
//
//            try {
//                //서버에 파일을 저장하는 메서드
//                file.transferTo(saveFile);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
            FileUtils.uploadFile(file, uploadPath);

        }
        return "";
    }


    //비동기로 전송된 파일 업로드 처리
    // @RestController가 아닌 그냥 @Controller에서 비동기를 처리하려면
    // @ResponseBody를 메서드에 붙여주세요
    @PostMapping("/ajaxUpload")
    @ResponseBody
    public ResponseEntity<String[]> ajaxUpload(List<MultipartFile> files) {

        //업로드된 파일 수만큼 배열의 길이를 설정
        int len = (files == null) ? 0 : files.size();

        //업로드 완료된 파일명을 저장할 배열
        String[] fileNameList = new String[len];
        for (int i = 0; i < len; i++) {
            MultipartFile file = files.get(i);
            log.info(file.getOriginalFilename());
            log.info(file.getSize());
            System.out.println("=================================================");

            fileNameList[i] = FileUtils.uploadFile(file, uploadPath);
        }
        return new ResponseEntity<>(fileNameList, HttpStatus.CREATED);
    }



}
