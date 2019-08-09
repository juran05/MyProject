마이바티스 DB를 가지고 SAX형태 XML파싱 연습하기
이번달내내 마이바티스, 스프링구조 파악하기...


오늘은 마이바티스 DB를 가지고 게시판을 만드는데
-insert,update,delete 어떻게 하는지 잘보기

JDBC
DBCP
MyBatis [DML] 버전이 2개/ XML과 어노테이션

DML => insert,update,delete 여기서 중요시 봐야하는것 : ★parameterType 설정하는 부분
			find												: ★ 수정할때 원래는 UPDATE board SET name=?
																     마이바티스는 UPDATE board SET name=#{name } #네임에= $｛｝
																     # => 일반데이터
																     $ = > table명, column명
			sequence											: ★<selectKey>
			
																SELECT <select>
																INSERT <insert>
																UPDATE <update>
																DELETE <delete>
																		  <sql>			: 중복되는 SQL문장
																		  <statement>	: 여러개의 문장을 동시에 수행

--2019.08.08 Mybatis

CREATE TABLE board(
	no NUMBER,
	name VARCHAR2(34) CONSTRAINT board_name_nn NOT NULL,
	subject VARCHAR2(1000) CONSTRAINT board_subject_nn NOT NULL,
	content CLOB CONSTRAINT board_content_nn NOT NULL,
	pwd VARCHAR2(10) CONSTRAINT board_pwd_nn NOT NULL,
	regdate DATE DEFAULT SYSDATE,
	hit NUMBER DEFAULT 0,
	CONSTRAINT board_no_pk PRIMARY KEY(no)
);


INSERT INTO board(no,name,subject,content,pwd)
VALUES(1,'보라돌이','마이바티스 DML연습','마이바티스 DML연습','1234');
COMMIT;




1. interface, 추상클래스
	=======
	★★★관련된 클래스를 모아서 관리할 목적 [기술면접]
		
		1. 미완성 클래스→ 프로그래머가 완성해서 사용
		2. interface : 모든 메소드를 구현한다(1.7버전) ▶1.8부터는 추상클래스를 쓰지말고 인터페이스를 쓴다
		
		interface A {
			int a;	// 인터페이스는 상수형 변수, 반드시 int a = 10; 이렇게 값을 입력해야한다
																└> public static final int a = 10; 이건데 public static final 생략됌
			void dispaly(); ←오류나는 코딩
				└> public abstract void display() 이건데 public abstract 생략됌
			}
		
		***오버라이딩
			1) 상속이 있어야한다 (exetends, implements)
			2) 메소드명이 동일해야한다
			3) 리턴형과 매개변수가 동일해야한다
			4) ★축소는 불가능, 확장은 가능(접근지정어)
			 	=====================> private < default < protected < public
			
		***예외처리 : 목적) 비정상종료를 방지하고 정상상태를 유지한다
						기능) 사전에 에러를 방지한다
						방식) 복구(try~catch) , 회피(throws)
						
						사용자 입력값 실수 OR 프로그래머 실수
						
						상속도
						Object
							|
						Throwable
							|
				(수정불)Error, Exception(수정유)
									|
								CheckException	UnCheckException
								============	============== RuntimeException
NumberFormatException	=	IOException
NullPointerException			=	SQLException
ArrayIndexOutofBoundsException	=	ClassNotFoundException
ClassCastException			=	MalformURLException
								
		***Class Class로 extends
			interface interface로 extends
			interface Class로 implements	
			Class interface로 X 이런거없음
			
				interface 설계만된거고 구체적인 구현은 안된것
				그래서 Class에서 interface 상속은 없다
				
				
===========================================================================================		
순서]
요청 → DispatcherServlet → Model → DispatcherServlet → jsp
								===
								MVC동작!!!
								
								
dom파싱
	단점 : 속도가 느리다 
			========== XML을 읽어서 메모리에 트리형태로 저장
							========================

	장점 : 수정, 삭제, 추가가 가능

sax파싱 framework(sax) → Spring, Mybatis 모두 삭스파싱~
	장점 : 메모리에 저장은 안하고 한줄씩 읽어서 데이터만 추출해내기에 속도가 빠르다

JAXP = XML	// DOM(저장했다가 수정삭제가능)/SAX(데이터만 읽어드림)
JAXB = XML Binding(대용량-빅데이터)


DispatcherServlet [컨트롤러] 요청만 받고 처리한 결과값만 보내줌				...요청처리하기 위해 모델을 준것
HandlerMapping [XML을파싱하는곳(읽어가는곳)]
WebApplicationContext [클래스를관리하는곳]

	DispatcherServlet(Controller) 얘가 원래 하던일
	----------------------
	요청을 받기
	
	요청을 처리(Model을찾는다)			→ 클래스로 뺌(WebApplicationContext) 이놈이!!! application-context.xml를 관리한다!!!
	요청을 처리 후 결과값 가지고 오기		----------------------
	결과값 전송하기 위해(JSP를 찾는다)	→ 클래스로 뺌(ViewResolver)
												----------------------> 삼위일체 스프링!!!
	찾은 JSP (request로 결과값 전송)

	이렇게 나눈다 처리 속도를 빠르게 하기 위해서


네트워크, 웹 = 스레드가 무조건 들어감!!!

프로그램은 대부분 C/S프로그램이 중심이다


===========================================================================================
각 폴더,클래스 만들고
제일먼저 web-inf에 application-context 클래스등록 후
web.xml에서 경로명을 보내준후

controller로 간다!!! 그안에서 

DispatcherServlet → 클래스등록 파일을 전송을 해줘야한다
	<init-param> 안에 경로명까지 등록!!!
						
					
HandlerMapping 					-qname(태그명) , attributes(속성) 두개만 알고넘어가기
WebApplicationContext 클래스	-getBean(클래스를찾음) 클래스가 저장이 되는데 id를 넣으면 객체를 찾아준다



DispatcherServlet 서블렛 생성하기

HandlerMapping 에서 DefaultHandler상속받기
SAX파싱을 할거다 파싱!!!!
소스-오버라이드 안에서 startElement 클릭후 피니쉬 
왜하냐? 태그 가져와서 재정의할려고
		
	현재 SAX 코딩은 이렇다 ex)
	<?xml version="1.0" encoding="UTF-8"?>		1. 맨처음에 얘를 읽자마자 메소드를 호출한다 -> startDocument()
	<book>				2. startElement() 태그가 시작됐다
		<list>				3. startElement() 태그가 시작됐다
			<title>			4. startElement() 태그가 시작됐다
			aaa			5. characters()
			</title>		6. endElement()
			<price>		7. 스타트엘리먼트
			1000			8. 캐릭터
			</price>		9. 엔드엘리먼트
		</list>				10. 엔드엘리먼트
	</book>				11. 엔드엘리먼트
			12.endDocument()
			
			
이제 일로 WebApplicationContext 가라
디스패처서블릿으로 가라



board-mapper.xml sql문장
conifig.xml 연결
BoardVO 만들고 BoardDAO만들기

프로퍼티스는 파일로만든다..여기서는
앞 (값 ) 뒤 (키)


오후 날짜가 지멋대로 나올경우
SimpleDateFormat을 쓰는데
위에     <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 추가하고
변환하고자 하는 날짜에 <fmt:formatDate value="${vo.regdate }" pattern="YYYY-MM-dd"/>를 올려준다
