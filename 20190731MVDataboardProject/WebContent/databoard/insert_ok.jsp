<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="com.sist.model.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="model" class="com.sist.model.Model"/>


<!-- Model로 가서 자바코딩 하세요!!! -->

<%
	model.databoard_insert_ok(request, response); //모든 처리는 Model안에서 처리함, 현재 jsp는 넘겨만 주는곳...
%>

<c:redirect url="list.jsp"/> <!-- ←response.sendRedirect() -->