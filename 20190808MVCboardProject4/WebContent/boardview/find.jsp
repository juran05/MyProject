<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
    
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
		<h2 class="text-center">검색결과</h2>
		<div class="row">
			<table class="table">
				<tr>
					<td class="text-left">
						<a href="list.do" class="btn btn-sm btn-success">목록</a><br>
						검색결과 : ${count } 건입니다.
					</td>	
				</tr>
			</table>
			
			<c:if test="${count==0 }">
			<table class="table">
				<tr>
					<td class="text-center">검색한 결과가 없습니다</td>
				</tr>
			</table>
			</c:if>
			
			<c:if test="${count>0 }">
			<table class="table">
				<tr class="danger">
					<th width="10%" class="text-center">번호</th>
					<th width="45%" class="text-center">제목</th>
					<th width="15%" class="text-center">이름</th>
					<th width="20%" class="text-center">작성일</th>
					<th width="10%" class="text-center">조회수</th>
				</tr>
				<c:forEach var="vo" items="${list }" varStatus="s">
					<tr class="${s.index%2==0?'':'warning' }">
						<td width="10%" class="text-center">${vo.no }</td> 
						<td width="45%" class="text-left"><a href="detail.do?no=${vo.no }">${vo.subject }</a></td>
						<td width="15%" class="text-center">${vo.name }</td>
						<td width="20%" class="text-center">
							<fmt:formatDate value="${vo.regdate }" pattern="YYYY-MM-dd"/></td>
							<%--SimpleDateFormat = fmt:formatDate --%>
						<td width="10%" class="text-center">${vo.hit }</td>
					</tr>
				</c:forEach>	
			</table>
			</c:if>