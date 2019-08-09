<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="com.sist.model.*"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <jsp:useBean id="model" class="com.sist.model.Model"/> 
    <!--
     컨트롤러를 만드는순간 얘도 사라짐, 유즈빈은 모델을 받아서 출력하는 역활만 함 
    Model 이 ==> Controller 의 역활 , 모델을 통해서 리퀘스트(←모델에서 값은 채워짐)를 받아 옴...
    
    Model.java = 데이터 처리하는 과정(Model)
    list.jsp = 처리한 과정을 받아 화면만 출력(View)
    -->
    <%
    	model.databoardList_list(request);
    %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="css/bootstrap.min.css">
<style type="text/css">
	.row {
		margin: 0px auto;
		width: 900px;
	}
</style>
</head>
<body>
	<div class="container">
		<h2 class="text-center">자료실</h2>
		<div class="row">
			<table class="table">
				<tr>
					<td class="text-left">
						<a href="insert.jsp" class="btn btn-sm btn-success">새글</a>
					</td>	
					<td class="text-right">
						${curpage } page / ${totalpage } pages
					</td>	
				</tr>
			</table>
			<table class="table">
				<tr class="danger">
					<th width="10%" class="text-center">번호</th>
					<th width="45%" class="text-center">제목</th>
					<th width="15%" class="text-center">이름</th>
					<th width="20%" class="text-center">작성일</th>
					<th width="10%" class="text-center">조회수</th>
				</tr>
				<c:forEach var="vo" items="${list }" varStatus="s">
					<tr class="${s.index%2==0?'':'warning' }"> <!-- 0은 list에 대한 인덱스번호다...액티브 : 흰색 / 워닝 : 주황색 -->
						<td width="10%" class="text-center">${vo.no }</td> <!-- getNO가 들어감 ㅎㅎㅎ -->
						<td width="45%" class="text-left"><a href="detail.jsp?no=${vo.no }&page=${curpage}">${vo.subject }</a></td>
						<td width="15%" class="text-center">${vo.name }</td>
						<td width="20%" class="text-center">${vo.regdate }</td>
						<td width="10%" class="text-center">${vo.hit }</td>
					</tr>
				</c:forEach>	
			</table>
			
<!-- 			총페이지만큼 1~5까지 뿌리고 들어간다
			그래서 총페이지를 갖고 오는 DB를 만들것
			현재페이지때 active.. -->
			<table class="table">
				<tr>
					<td class="text-center">
						<ul class="pagination">
						  <li><a href="list.jsp?page=1">&lt;&lt;</a></li>
						  <li><a href="#">&lt;</a></li>
							<c:forEach var="i" begin="1" end="${totalpage }">
								
								<li class="${i==curpage?'active':'' }"><a href="list.jsp?page=${i }">${i }</a></li>
							
							</c:forEach>
						  <li><a href="#">&gt;</a></li>
						  <li><a href="list.jsp?page=${totalpage }">&gt;&gt;</a></li>
						</ul>
					</td>
				</tr>
			</table>
		</div>
	</div>
</body>
</html>