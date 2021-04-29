<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>

<script>
    const data = '${result}';
    console.log('result:', data);

    if (data === 'idFail') {
        alert('회원가입된 아이디가 아닙니다. 회원가입을 먼저 진행해주세요!');
        history.back(); //뒤로가기
    } else if (data === 'pwFail') {
        alert('비밀번호가 틀렸습니다.');
        history.back(); //뒤로가기
    }
</script>