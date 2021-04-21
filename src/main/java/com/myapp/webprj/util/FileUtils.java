package com.myapp.webprj.util;

import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

//파일 업로드 다운로드관련 공통처리를 위한 클래스
public class FileUtils {

    //1. 사용자가 파일을 업로드했을 때 저장 파일명을 만들어서 리턴하는 메서드
    /**
     * @param file - 클라이언트가 올린 파일 정보
     * @param uploadPath  - 서버의 업로드 루트 디렉토리 ex) D:/developing/upload
     * @return 완성된 파일저장 경로
     */
    public static String uploadFile(MultipartFile file, String uploadPath) {

        //중복이 없는 파일명으로 변경
        //ex) dog.jpg  =>  3dfshjfh334-dfdsfd43-qwecvx44-3442dd_dog.jpg
        String newFileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();

        return "";
    }
}
