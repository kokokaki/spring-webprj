package com.myapp.webprj.util;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class FileUtilsTest {

    @Test
    void uuidTest() {
        String fileName = "dog.jpg";
        String newFileName = UUID.randomUUID().toString() + "_" + fileName;
        System.out.println("newFileName = " + newFileName);
    }

    @Test
    void makeNewUploadPathTest() {
        String newUploadPath = FileUtils.getNewUploadPath("D:\\developing_hsg\\upload");
        System.out.println("newUploadPath = " + newUploadPath);
    }

}