<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@include file="../includes/header.jsp"%>


<div class="row">
    <div class="col-lg-12">
        <h1 class="page-header">Board Read</h1>
    </div>
    <!-- /.col-lg-12 -->
</div>
<!-- /.row -->

<div class="row">
    <div class="col-lg-12">
        <div class="panel panel-default">

            <div class="panel-heading">Board Read Page</div>
            <!-- /.panel-heading -->
            <div class="panel-body">

                <div class="form-group">
                    <label>Bno</label> <input class="form-control" name='bno' value="${board.bno}" readonly>
                </div>

                <div class="form-group">
                    <label>Title</label> <input class="form-control" name='title' value="${board.title}" readonly>
                </div>

                <div class="form-group">
                    <label>Text area</label>
                    <textarea class="form-control" rows="5" name='content' readonly>${board.content}</textarea>
                </div>

                <div class="form-group">
                    <label>Writer</label> <input class="form-control" name='writer' value="${board.writer }" readonly>
                </div>


                <button id='modify-btn' class="btn btn-default">수정</button>
                <button id='list-btn' class="btn btn-info">목록</button>



            </div>
            <!--  end panel-body -->

        </div>
        <!--  end panel-body -->
    </div>
    <!-- end panel -->
</div>
<!-- /.row -->

<!-- 댓글 영역 -->
<div class='row'>
    <div class="col-lg-12">

        <!-- /.panel -->
        <div class="panel panel-default">
            <div class="panel-heading">
                <i class="fa fa-comments fa-fw"></i> 댓글 (<span class="replyCnt"></span>)
                <button id='addReplyBtn' class='btn btn-primary btn-xs pull-right'>댓글 등록</button>
            </div>

            <!-- /.panel-heading -->
            <div class="panel-body">

                <ul class="chat">
                    <!-- 실제 댓글이 들어갈 부분 -->
                    <!-- <li class="left clearfix" data-rno="1">
            <div>
              <div class="header">
                <strong class="primary-font">둘리</strong>
                <small class="pull-right text-muted">13:09</small>
              </div>
              <p>댓글 내용 주저리주저리</p>
            </div>
          </li> -->


                </ul>
                <!-- ./ end ul -->
            </div>
            <!-- /.panel .chat-panel -->

            <!-- 댓글 페이지가 들어갈 부분 -->
            <div class="panel-footer"></div>


        </div>
    </div>
    <!-- ./ end row -->
</div>



<!-- Modal -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="myModalLabel">REPLY MODAL</h4>
            </div>
            <div class="modal-body">
                <div class="form-group">
                    <label>Reply</label>
                    <input class="form-control" name='reply' value='New Reply!!!!'>
                </div>
                <div class="form-group">
                    <label>Replyer</label>
                    <input class="form-control" name='replyer' value='replyer'>
                </div>
                <div class="form-group">
                    <label>Reply Date</label>
                    <input class="form-control" name='replyDate' value='2018-01-01 13:13'>
                </div>

            </div>
            <div class="modal-footer">
                <button id='modalModBtn' type="button" class="btn btn-warning">수정</button>
                <button id='modalRemoveBtn' type="button" class="btn btn-danger">삭제</button>
                <button id='modalRegisterBtn' type="button" class="btn btn-primary">등록</button>
                <button id='modalCloseBtn' type="button" class="btn btn-default">Close</button>
            </div>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal-dialog -->
</div>
<!-- /.modal -->


<!-- //댓글 영역 끝 -->



<!-- 댓글 관련 스크립트 -->
<script>
    //원본 글 번호
    let bno = '${board.bno}';

    //날짜 포맷 변환 함수
    function formatDate(datetime) {
        //문자열 날짜 데이터를 날짜객체로 변환
        const dateObj = new Date(datetime);
        // console.log(dateObj);
        //날짜객체를 통해 각 날짜 정보 얻기
        let year = dateObj.getFullYear();
        let month = dateObj.getMonth() + 1;
        let day = dateObj.getDate();
        let hour = dateObj.getHours();
        let minute = dateObj.getMinutes();

        //오전, 오후 시간체크
        let ampm = '';
        if (hour < 12 && hour >= 6) {
            ampm = '오전';
        } else if (hour >= 12 && hour < 21) {
            ampm = '오후';
            if (hour !== 12) {
                hour -= 12;
            }
        } else if (hour >= 21 && hour <= 24) {
            ampm = '밤';
            hour -= 12;
        } else {
            ampm = '새벽';
        }

        //숫자가 1자리일 경우 2자리로 변환
        (month < 10) ? month = '0' + month: month;
        (day < 10) ? day = '0' + day: day;
        (hour < 10) ? hour = '0' + hour: hour;
        (minute < 10) ? minute = '0' + minute: minute;

        return year + "-" + month + "-" + day + " " + ampm + " " + hour + ":" + minute;

    }

    //댓글 페이지 목록을 만들어주는 함수
    function showReplyPage(count) {
        let pageNum = 1;
        const $pageFooter = document.querySelector('.panel-footer');

        //한번에 보여줄 페이지 개수
        const DISPLAY_PAGE = 10;

        let endPage = Math.ceil(pageNum / DISPLAY_PAGE) * DISPLAY_PAGE;
        const beginPage = endPage - DISPLAY_PAGE + 1;

        //끝페이지 보정
        if (endPage * DISPLAY_PAGE >= count) {
            endPage = Math.ceil(count / DISPLAY_PAGE);
        }

        const prev = beginPage !== 1;
        const next = endPage * DISPLAY_PAGE < count;

        let data = '';
        data += '<ul class="pagination pull-right">';

        if (prev) {
            data += '  <li class="page-item"><a class="page-link" href="' + (beginPage - 1) + '">이전</a></li>';
        }

        for (let i = beginPage; i <= endPage; i++) {
            let active = pageNum == i ? "active" : '';

            data += '<li class="page-item ' + active + '"><a class="page-link" href="' + i + '">' + i + "</a></li>";
        }

        if (next) {
            data += '  <li class="page-item"><a class="page-link" href="' + (endPage + 1) + '">다음</a></li>';
        }
        data += '</ul>';

        $pageFooter.innerHTML = data;


        //댓글 페이지 클릭 이벤트
        document.querySelector('ul.pagination').addEventListener('click', e => {
            if (!e.target.matches('ul.pagination > li > a')) {
                return;
            }
            e.preventDefault();
            //console.log("페이지 버튼 클릭됨: ", e.target.getAttribute('href'));

            curPageNum = e.target.getAttribute('href');
            showReplyList(curPageNum);

        });
    }

    //댓글 목록 DOM을 만드는 함수
    function makeReplyListDOM({
        replies,
        count
    }) {

        if (replies === null || replies.length === 0) {
            return;
        }
        let data = '';

        for (let reply of replies) {
            data += '<li class="left clearfix" data-rno="' + reply.rno + '">';
            data += '   <div>';
            data += '     <div class="header">';
            data += '       <strong class="primary-font">' + reply.replyer + '</strong>';
            data += '         <small class="pull-right text-muted">' + formatDate(reply.replyDate) + '</small>';
            data += '     </div>';
            data += '     <p>' + reply.reply + '</p>';
            data += '   </div>';
            data += '</li>';
        }
        document.querySelector('.chat').innerHTML = data;

        //페이지화면을 그려주는 함수
        showReplyPage(count);

    }

    //댓글목록을 비동기로 불러오는 함수
    function showReplyList(page) {
        fetch('/api/v1/replies/' + bno + '/' + page)
            .then(res => res.json())
            .then(replyMap => {
                // console.log(replyList);
                makeReplyListDOM(replyMap);
                document.querySelector('.replyCnt').textContent = replyMap.count;
            });
    }

    let curPageNum = 1;

    (function () {

        showReplyList(curPageNum);

    }());
</script>

<!-- 게시글 관련 스크립트 -->
<script>
    $(document).ready(function () {

        //목록 버튼 이벤트
        document.getElementById('list-btn').addEventListener('click', e => {
            location.href =
                '/board/list?page=${pageInfo.page}&type=${pageInfo.type}&keyword=${pageInfo.keyword}';
        });

        //수정 버튼 이벤트
        document.getElementById('modify-btn').addEventListener('click', e => {
            location.href =
                '/board/modify?page=${pageInfo.page}&type=${pageInfo.type}&keyword=${pageInfo.keyword}&bno=${board.bno}';
        });

    });
</script>


<%@include file="../includes/footer.jsp"%>