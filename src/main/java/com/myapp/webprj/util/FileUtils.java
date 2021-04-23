package com.myapp.webprj.util;

import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

//파일 업로드 다운로드관련 공통처리를 위한 클래스
public class FileUtils {

    //이미지 구분을 위한 이미지 확장자 맵 만들기
    private static final Map<String, MediaType> mediaMap;

    static {
        mediaMap = new HashMap<>();
        mediaMap.put("JPG", MediaType.IMAGE_JPEG);
        mediaMap.put("PNG", MediaType.IMAGE_PNG);
        mediaMap.put("GIF", MediaType.IMAGE_GIF);
    }

    //확장자를 알려주면 미디어타입을 리턴하는 메서드
    public static MediaType getMediaType(String ext) {
        ext = ext.toUpperCase();
        if (mediaMap.containsKey(ext)) {
            return mediaMap.get(ext);
        } else {
            return null;
        }
    }


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

        //저장 경로에 날짜별 폴더를 생성
        //ex) uploadPath :  ~~~~/upload
        // -> newUploadPath: ~~~~/upload/2021/04/21
        String newUploadPath = getNewUploadPath(uploadPath);

        //업로드 수행
        File saveFile = new File(newUploadPath, newFileName);
        try {
            file.transferTo(saveFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String resFileName = newUploadPath.substring(uploadPath.length()) + File.separator + newFileName;

        //업로드에 성공하면 해당 파일의 루트패스를 제외한 전체 경로를 리턴
        return resFileName.replace("\\", "/");
    }

    //날짜명으로 폴더 생성
    public static String getNewUploadPath(String uploadPath) {
        LocalDate now = LocalDate.now();
        int y = now.getYear();
        int m = now.getMonthValue();
        int d = now.getDayOfMonth();

        //폴더 생성
        //File.separator : 운영체제에 맞게 디렉토리 경로를 만들어줌 (윈도우: \\, 리눅스: /)
        String newUploadPath = uploadPath;
        String[] dateInfo = {String.valueOf(y), len2(m), len2(d)};
        for (String date : dateInfo) {
            newUploadPath += File.separator + date;
            //이 경로대로 폴더를 생성
            File dirName = new File(newUploadPath);
            if (!dirName.exists()) {
                dirName.mkdir();
            }
        }
        return newUploadPath;
    }

    //한자리수 월, 일을 항상 2자리로 표현해주는 메서드
    private static String len2(int n) {
        return new DecimalFormat("00").format(n);
    }

    //2. 파일명에서 확장자를 추출해주는 메서드
    public static String getFileExtension(String fileName) {
        //ex) fileName: kdfksjdf34-fdsfdsf-342342-dfsdf_cat.jpg
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

}
