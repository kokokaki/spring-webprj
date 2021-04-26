<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@include file="../includes/header.jsp"%>

<style>
    ul > li {
        list-style: none;
    }
    .fileDrop {
        width: 600px;
        height: 200px;
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


<div class="row">
    <div class="col-lg-12">
        <h1 class="page-header">Board Register</h1>
    </div>
    <!-- /.col-lg-12 -->
</div>
<!-- /.row -->

<div class="row">
    <div class="col-lg-12">
        <div class="panel panel-default">

            <div class="panel-heading">Board Register</div>
            <!-- /.panel-heading -->
            <div class="panel-body">

                <form role="form" action="/board/register" method="post">
                    <div class="form-group">
                        <label>Title</label> <input class="form-control" name='title'>
                    </div>

                    <div class="form-group">
                        <label>Text area</label>
                        <textarea class="form-control" rows="5" name='content'></textarea>
                    </div>

                    <div class="form-group">
                        <label>Writer</label> <input class="form-control" name='writer'>
                    </div>

                    <!-- 첨부파일 드래그 앤 드롭 영역 -->
                    <div class="form-group">
                        <div class="fileDrop">
                            <span>Drop Here!!</span>
                        </div>
                        <div class="uploadDiv">
                            <input type="file" name="files" id="ajax-file" style="display:none;">
                        </div>
                        <!-- 업로드된 파일의 썸네일을 보여줄 영역 -->
                        <div class="uploaded-list">

                        </div>
                    </div>

                    <button type="submit" class="btn btn-default">Submit
                        Button</button>
                    <button type="reset" class="btn btn-default">Reset Button</button>
                </form>

            </div>
            <!--  end panel-body -->

        </div>
        <!--  end panel-body -->
    </div>
    <!-- end panel -->
</div>
<!-- /.row -->

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


        //이미지파일인지 확인하는 함수
        function isImageFile(originFileName) {
            //정규표현식
            const pattern = /jpg$|gif$|png$/i;
            return originFileName.match(pattern);
        }

        //확장자 판별 후 태그처리 함수
        function checkExtType(fileName) {
            //원본 파일명 추출
            //fileName: /2021/04/23/~~~~~~~~~~.확장자
            // dfslkdfsjlfjsdl_haha.docx  ->  haha.docx
            let originFileName = fileName.substring(fileName.indexOf("_") + 1);


            //input과 이미지를 감싸는 li
            const $li = document.createElement('li');
            const $input = document.createElement('input');
            $input.setAttribute('type', 'hidden');
            $input.setAttribute('name', 'fileNames');
            $input.setAttribute('value', fileName);
            $li.appendChild($input);

            //이미지인지 확인
            if(isImageFile(originFileName)) {
                originFileName = fileName.substring(fileName.indexOf("_") + 1);

                const $img = document.createElement('img');
                $img.classList.add('img-sizing');
                $img.setAttribute('src', '/loadFile?fileName=' + fileName);
                $img.setAttribute('alt', originFileName);

                $li.appendChild($img);

                $('.uploaded-list').append($li);
            } else {
                //이미지가 아니라면 다운로드 링크를 생성
                const $link = document.createElement('a');
                $link.setAttribute('href', '/loadFile?fileName=' + fileName);
                
                const $img = document.createElement('img');
                $img.classList.add('img-sizing');
                $img.setAttribute('src', '/img/file_icon.jpg');

                $link.appendChild($img);
                $link.innerHTML += '<span>' + originFileName + '</span>';

                $li.appendChild($link);

                $('.uploaded-list').append($li);
            }
        }

        //드롭한 파일의 형식에 따라 태그를 보여주는 함수
        function showFileData(fileNameList) {
            //fileName: \2021\04\22\dfhskdfjslfjdlsfsjk_dog.gif
            for (let fileName of fileNameList) {

                //이미지인지 이미지가 아닌지 구분하여 따로 처리
                checkExtType(fileName);
                
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
                    console.log(fileNameList);
                    showFileData(fileNameList);
                });

        });
    });
</script>


<%@include file="../includes/footer.jsp"%>