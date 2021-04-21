<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>

<%@include file="../includes/header.jsp"%>

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
</head>
<body>
    
    <!-- 파일 업로드를 위한 form -->
    <form action="/upload" method="post" enctype="multipart/form-data">
        <input type="file" name="file" multiple>
        <button type="submit">업로드</button>
    </form>

</body>
</html>