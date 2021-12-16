<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>cafeCarp 로그인</title>
<link href="<c:url value="/resources/css/userDiv.css" />" rel="stylesheet" type="text/css" />
<script src="https://code.jquery.com/jquery-latest.min.js"></script>
</head>
<body>
<div class="container-main">
<div class="header-main">
	<a href="<c:url value="/cafeCarp/main" />"><img alt="cafeCarp" src="<c:url value="/resources/img/cafeCarp_main.png" />"></a>
</div>
<div class="main-login">
	<form action="<c:url value="/cafeCarp/login" />" method="post">
	<input size="50" type="text" id="phone" name="phone" oninput="autoHyphen(this)" maxlength="13" placeholder="'-'를 제외한 숫자 11자리 입력"/>
	<button type="submit" id="subBtn" disabled="true">회원 주문하기</button>
	</form>
</div>	
</div>
<script type="text/javascript">
$(function(){
	$("#phone").on('input', function(){
		if($("#phone").val()==''){
			$("#subBtn").attr("disabled", true);
		}else{
			$("#subBtn").attr("disabled", false);
		}
	})
});
const autoHyphen = (target) => {
	 target.value = target.value
	   .replace(/[^0-9]/, '')
	   .replace(/^(\d{2,3})(\d{3,4})(\d{4})$/, `$1-$2-$3`);
	}
</script>
</body>
</html>