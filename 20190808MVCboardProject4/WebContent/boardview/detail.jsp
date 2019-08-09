<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
    <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="http://code.jquery.com/jquery.js"></script>
<script type="text/javascript">
var i = 0;
$(function(){
	$('.del_update').click(function() {
		var no=$(this).attr("value");
		$('.dels').hide();
		if(i==0) {
			$('#del'+no).show();
			$(this).text("취소");
			i=1;
		} else {
			$('#del'+no).hide();
			$(this).text("수정");
			i=0;
		}
	});
});
</script>
<link rel="stylesheet" href="css/bootstrap.min.css">
<style type="text/css">
	.row {
		margin: 0px auto;
		width: 700px;
	}
</style>
</head>
<body>
	<div class="container">
		<h2 class="text-center">내용보기</h2>
		<div class="row">
			<table class="table">
				<tr>
					<th class="text-center success" width="20%">번호</th>
					<td class="text-center" width="30%">${vo.no }</th>
					<th class="text-center success" width="20%">작성일</th>
					<th class="text-center" width="30%">
						${vo.dbday }
						<%-- <fmt:formatDate value="${vo.regdate }" pattern="YYYY-MM-dd"/> --%>
					</th>
				</tr>
				<tr>
					<th class="text-center success" width="20%">이름</th>
					<td class="text-center" width="30%">${vo.name }</th>
					<th class="text-center success" width="20%">조회수</th>
					<td class="text-center" width="30%">${vo.hit }</th>
				</tr>
				<tr>
					<th class="text-center success" width="20%">제목</th>
					<td class="text-left" colspan="3">${vo.subject }</th>
				</tr>
				<tr>
					<td colspan="4" class="text-left" valign="top" height="200">${vo.content }</td>
				</tr>
				<tr>
					<td colspan="4" class="text-right">
						<a href="update.do?no=${vo.no }" class="btn btn-sm btn-success">수정</a>
						<a href="delete.do?no=${vo.no }" class="btn btn-sm btn-warning">삭제</a>
						<a href="list.do" class="btn btn-sm btn-success">목록</a>
					</td>
				</tr>
				
			</table>
		</div>
	</div>
</body>
</html>