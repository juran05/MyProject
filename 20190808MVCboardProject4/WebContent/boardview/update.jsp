<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
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
		<h2 class="text-center">수정하기</h2>
		<div class="row">
		<form method="post" action="update_ok.do"> 
			<table class="table">
				<tr>
					<th class="text-right info" width="15%">이름</th>
					<td width="85%">
						<input type="text" name="name" size="15" class="input-sm" value="${vo.name }">
						
						<!-- 수정이나 삭제는 꼭 히든이 들어가야 합니다!!! -->
						<input type="hidden" name="no" value="${vo.no }">
						
					</td>
				</tr>
				<tr>
					<th class="text-right info" width="15%">제목</th>
					<td width="85%">
						<input type="text" name="subject" size="45" class="input-sm" value="${vo.subject }">
					</td>
				</tr>
				<tr>
					<th class="text-right info" width="15%">내용</th>
					<td width="85%">
						<textarea rows="10" cols="55" name="content">${vo.content }</textarea>
					</td>
				</tr>
				<tr>
					<th class="text-right info" width="15%">비밀번호</th>
					<td width="85%">
						<input type="password" name="pwd" size="10">
					</td>
				</tr>
				<tr>
					<td colspan="2" class="text-center">
						<input type="submit" value="수정" class="btn btn-sm btn-success" >  <!-- 원래는 입력한걸 검색해서 확인해봐야해서 submit주면 안됌.. 지금은 대충만드느라.. 화면나누는거에 주력하고있어서...-->
						<input type="button" value="취소" class="btn btn-sm btn-danger" onclick="javascript:history.back()"> 
					</td>
				</tr>
			</table>
			</form>
		</div>
	</div>
</body>
</html>