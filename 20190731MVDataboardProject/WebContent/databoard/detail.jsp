<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="com.sist.model.*"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
    <jsp:useBean id="model" class="com.sist.model.Model"></jsp:useBean>
    
    <%
    	// 모델을 통해서 상세보기 가져오기
    	// DAO와 Model.java 에서 메소드 만들기
    	// Model.jsp에서 만든 메소드 불러오기
    	model.databoard_detail(request);
    %>
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
					<td class="text-center" width="30%">${vo.regdate }</th>
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
				<c:if test="${vo.filesize>0 }"><!-- ------조건에 맞으면 출력 -->
				<tr>
					<th class="text-center success" width="20%">첨부파일</th>
					<td class="text-left" colspan="3">
						<a href="download.jsp?fn=${vo.filename }">${vo.filename }</a>(${vo.filesize }Bytes)
					</th>
				</tr>
				</c:if>
				<tr>
					<td colspan="4" class="text-left" valign="top" height="200">${vo.content }</td>
				</tr>
				<tr>
					<td colspan="4" class="text-right">
						<a href="update0801.jsp?no=${vo.no }&page=${curpage}" class="btn btn-sm btn-success">수정</a>
						<a href="#" class="btn btn-sm btn-warning">삭제</a>
						<a href="list.jsp?page=${curpage }" class="btn btn-sm btn-success">목록</a>
					</td>
				</tr>
				
			</table>
		</div>
		<div class="row">
			<h3>댓글</h3>
			
			<table class="table">
				<c:if test="${len==0 }">
					<tr>
						<td>댓글이 없습니다</td>
					</tr>
				</c:if>
				<c:if test="${len>0 }">
					<tr>
						<td>
							<c:forEach var="rvo" items="${list }">
								<table class="table">
									<tr>
										<td class="text-left">${rvo.name }(${rvo.dbday })</td>
										<td class="text-right">
											<c:if test="${sessionScope.id==rvo.id }">
												<a href="#" class="btn btn-xs btn-success del_update" value="${rvo.no }">수정</a>
												<a href="reply_delete0801.jsp?no=${rvo.no}&bno=${vo.no}&page=${curpage}" class="btn btn-xs btn-warning">삭제</a>
											</c:if>
										</td>
									</tr>
									<tr>
										<td colspan="2" valign="top">
											<pre style="background-color: rgb(92,70,45); opacity:0.5; color:#FFF">${rvo.msg }</pre>
										</td>
									</tr>
									<tr id="del${rvo.no }" style="display:none" class="dels">
										<td colspan="2">
											<form action="reply_update0801.jsp" method="post">
												<textarea rows="3" cols="70" name="msg" style="float:left">${rvo.msg }</textarea>
												<input type="hidden" name="bno" value="${vo.no }">
												<input type="hidden" name="page" value="${curpage }">
												<input type="hidden" name="no" value="${rvo.no }">
												<input type=submit value="수정하기" style="height:60px" class="btn btn-sm btn-success">
											</form>
										</td>
									</tr>
								</table> 
							</c:forEach>
						</td>
					</tr>
				</c:if>
			</table>
			
			<table class="table">
				<tr>
					<td>
						<form action="reply_insert0801.jsp" method="post">
							<textarea rows="3" cols="70" name="msg" style="float:left"></textarea>
							<input type="hidden" name="bno" value="${vo.no }">
							<input type="hidden" name="page" value="${curpage }">
							<input type=submit value="댓글쓰기" style="height:60px" class="btn btn-sm btn-success">
						</form>
					</td>
				</tr>
			</table>
		</div>
	</div>
</body>
</html>