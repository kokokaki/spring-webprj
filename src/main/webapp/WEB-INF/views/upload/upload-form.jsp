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

    <style>

        .fileDrop {
            width: 800px;
            height: 400px;
            border: 1px dashed gray;
            display: flex;
            justify-content: center;
            align-items: center;
            font-size: 1.5em;
        }
        .uploaded-list {
            display: flex;
        }
        .img-sizing {
            display: block;
            width: 100px;
            height: 100px;
        }
    </style>
</head>
<body>
    
    <!-- 파일 업로드를 위한 form -->
    <form action="/upload" method="post" enctype="multipart/form-data">
        <input type="file" name="file" multiple>
        <button type="submit">업로드</button>
    </form>

    <br>
    
    <!-- 비동기 통신을 통한 실시간 파일 업로드 -->
    <div class="fileDrop">
        <span>Drop Here!!</span>
    </div>

    <!-- 
        - 파일 정보를 서버로 보내기 위해서는 <input type="file"> 이 필요
        - 해당 input태그는 사용자에게 보여주어 파일을 직접 선택하게 할 것이냐
          혹은 드래그앤 드롭으로만 처리를 할 것이냐에 따라 display를 상태를 결정
     -->
     <div class="uploadDiv">
         <input type="file" name="files" id="ajax-file" style="display:none;">
     </div>

     <!-- 업로드된 파일의 썸네일을 보여줄 영역 -->
     <div class="uploaded-list">

     </div>

    <script>
        $(document).ready(function () {
            //drag & drop 이벤트
            const $dropBox = $('.fileDrop');
            
            //드래그 진입 이벤트
            $dropBox.on('dragover dragenter', e => {
                e.preventDefault();
                $dropBox.css('border-color', 'red').css('background', 'lightgray');
            });
            //드래그 탈출 이벤트
            $dropBox.on('dragleave', e => {
                e.preventDefault();
                $dropBox.css('border-color', 'gray').css('background', 'transparent');
            });

            //드롭한 파일의 썸네일을 보여주는 함수
            function showThumbnail(fileNameList) {
                //fileName: \2021\04\22\dfhskdfjslfjdlsfsjk_dog.gif
                for (let fileName of fileNameList) {
                    const $img = document.createElement('img');
                    $img.classList.add('img-sizing');
                    $img.setAttribute('src', '/loadFile?fileName=' + fileName);
                    $('.uploaded-list').append($img)
                }
            }

            //드롭 이벤트
            $dropBox.on('drop', e => {
                e.preventDefault();
                //alert('파일이 드롭됨!');

                //드롭된 파일을 서버로 전송
                //드롭된 파일 데이터 읽기
                const files = e.originalEvent.dataTransfer.files;
                //console.log('drop file data: ', files);

                //읽은 파일 데이터 input[type=file]에 저장
                const $fileInput = $('#ajax-file');
                $fileInput.prop('files', files);

                //console.log('input: ', $fileInput);
                
                //해당 데이터를 서버로 전송
                //파일을 전송하기 위해 필요한 FormData객체
                const formData = new FormData();
                const sendFiles = $fileInput[0].files;

                //전송할 파일들을 formData에 담아 비동기 요청을 함
                for (let file of sendFiles) {
                    formData.append('files', file);
                }

                const reqInfo = {
                    method: 'POST',
                    body: formData
                };
                fetch('/ajaxUpload', reqInfo)
                    .then(res => res.json())
                    .then(fileNameList => {
                        //console.log(fileNameList);
                        showThumbnail(fileNameList);
                    });
 
            });
        });
    </script>

</body>
</html>