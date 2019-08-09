<!-- 
알고가기!!!
1) EL 과 JSTL (화면출력)
2) MV → JAVA 와 JSP 분리 
3) Database → DAO 에서이뤄짐 
				====== 초점 맞춰 공부하기~
				
[화면출력] EL 출력하는 형식 : ${ } ←괄호속에 들어가는건(key) 

	request.setAttribute()		▶ request.getAttribute() 인데 이게 ${ }로 함축
	session.setAttribute()		▶ session.getAttribute() 인데 이게 ${ }로 함축
	application.setAttribute()	▶ application.getAttribute() 인데 이게 ${ }로 함축
	
	${ } ▷ 연산자(+), 문자열 결합(+=)
		{ } 볼록안에 들어가는건 get메소드를 호출하여 VO(getter,setter) 없으면 사용 못함!!
		
	
[내용] JSTL

	<c:set> 			변수설정
	<c:if>				if문
	<c:forEach>		for문
	<c:forTokens>	자르기
	<c:choose>		switch
	<c:redirect>		sendRedirect
	<fmt:formatDate>	날짜
	
	${fn:String메소드}	스트링이 갖고있는 모든 메소드... [SPRING 나중에해]


request.setAttribute() 는 새로운 값을 추가 할때 씀, 초기화가 아님!!!
계속 계속 더함...



SQL에 ed test4 에다가
추가하기!!!

-- 2019.08.01
CREATE TABLE member(
	id VARCHAR2(20) PRIMARY KEY,
	pwd VARCHAR2(10) NOT NULL,
	name VARCHAR2(30) NOT NULL
);

INSERT INTO member VALUES('nana','1234','나나');
INSERT INTO member VALUES('ddubi','1234','뚜비');
INSERT INTO member VALUES('bbo','1234','뽀');
INSERT INTO member VALUES('bora','1234','보라돌이');
INSERT INTO member VALUES('admin','1234','관리자');

COMMIT;

저장 후에 @test4 하기 ㅋㅋ연습테이블>

	
-->