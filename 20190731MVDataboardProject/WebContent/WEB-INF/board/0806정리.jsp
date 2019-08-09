MVC 구조	
컨트롤러가 JSP와 Java의 연결 역활,
Controller를 어떻게 화면단에서 부를것인가~


dao
Model	(사용자가 요청한것 처리)
view	(처리된 결과값 보여줌)
Controller	(처리된결과 넘겨줌)	1.사용자요청뭔지 2.요청에대한 값찾기 3.리퀘스트에 값담아서 넘기기
view ======만들고시작

------
1. 사용자요청
2. DispatcherServlet(Controller) --> request를 받음
3. DispatcherServlet에 request를 해당 Model로 전송
4. Model 에선 DAO를 연결해서 결과값을 request에 담아준다 
5. 결과값이 담겨있는 request를 DispatcherServlet(Controller)에서 JSP로 전송
6. jsp에서 EL(JSTL)을 이용해서 화면출력 !!!
------

*Model 찾고 JSP를 어떻게 찾아 연결(매칭)할것인가 잘 생각하기

	[1교시]
***MVC구조 일반게시판 만들기
1. 패키지컨트롤러-서블렛 만들기,, init이랑 service 만 들어가게
2. 패키지모델에서 인터페이스 Model 만들기 
3. 패키지모델에서 ListModel 제목쓰고 Add 눌러서 Model 추가하기
4. 패키지모델에서 InsertModel 제목쓰고 〃
5. 패키지모델에서 DetailModel 제목쓰고 〃
6. DispatcherServlet 을 WEB-INF web.xml 에서 등록해주고 가기!!! 확장자 do~
7. DispatcherServlet 가서 init 메소드에 사용자 보낸 요청어 적기 	
		private String[] strCmd={"list.do", "insert.do","detail.do "};
8. 마찬가지로 밑에다가 매칭시킬
 		private String[] strCls={
			"com.sist.model.ListModel",
			"com.sist.model.InsertModel",
			"com.sist.model.DetailModel"
		}; 배열 추가 , strCmd에서 요청값이 들어오면 strCls를 호출한다
		싱글턴으로 메모리할당 하기
		
		
	[2교시]	
9.	DispatcherServlet 가서 service 메소드에 코딩
10. ListModel 가서 출력하고자 하는부분과 보낼부분 정하기
11. jsp에서 실행후 http://localhost:8080/20190806MVCProject2/*.do *에 list.. 써보기 

...//index.jsp는 파일명을 안줘도 자동실행, 곧장 메인페이지로 넘어가게 만드는 기능
// web.xml 에 welcom이 있기 때문에!! = index.html도 똑같음

	[3,4교시]
	전화번호 ip
	전화선 port
	전화기 socket
	부재중인 전화 tcp(자동으로 끊어버림)
	
	>>>예약<<<
	
	동적바인딩 ( 고정된 값만 가져옴 )
	정적바인딩 ( 내가원하는 메소드를 호출못함 )
	바인딩 : 메소드호출
	
	스프링에서는 Dispatcher의 값 2개(메뉴판,실제음식)를 넘겨주면 됌...
		DAO에서 필요한 정보!!! 
		
		
		
	[5교시]
	
	 * Controller → 모든 웹 프로그램의 동작 , 컨트롤러가 중지되면 다 사용을 못해서 컨트롤러는 유지보수를 안한다, 수정하면 안되니까 파일을 이용한다 (ex.XML)
	 * XML의 단점은? 1.사용자 정의기 때문에 태그 이해시 시간이 오래걸림... 2.파일이 커질수록 속도가 느려진다...
	 * 		┗→ 어노테이션으로 변경이 된다~
	 
	 XML 과 어노테이션 Controller 쓰려면 필수로 해야함
	 
	1. MyBatis	:	XML,Annotation
		<select>SELECT...</select>
		@Select("list.do")
	2. Spring		:	XML,Annotation
		 <bean id="list.do" class="A">
		 @Component("list.do")
		 Class A
		 
	기술면접	 
	자료구조 (데이터를 저장해서 쉽게 가져올 수 있는 것) 
	List-중복허용, Map, Set [Collection 링크드리스트와 어레이리스트의 차이점?]
	
	web(클래스저장) => Map
	database(중복데이터연결) => List (ArrayList, Vector, LinkedList) 
	XML(네트워크:트리형태) => Set
	
	기술면접 
	벡터와 어레이리스트 의 차이점?
	동기화(Vector) 비동기화(ArrayList)의 차이 !
	
	모달과 모달레스의 차이 창띄우고 작업되냐 안되냐~ 차이 
	
	자바는~
	
	1) 변수
		*지역변수
			-지역변수
			-매개변수
					기능별로 따진다면? 3가지 누적변수,루프변수,플러그변수
		*전역변수(***)
			-인스턴스 변수()
			-정적 변수 static (공유변수)
			
			static으로 선언하는 순간 메모리에 바로 들어가고
			그냥 일반 변수는 new로 생성을 해줘야 메모리에 들어간다~
			
			new가 새로운 값을 넣어주는 것이고 생성자가 기존에 할당된 메모리에 값을 넣어준다
			
			A aa = new A();
			
	2) 연산자
		단항연산자 ++, --, !(부정)
		
		논리연산자	 &, ||
		
		삼항연산자
		
	3) 제어문
		 if, for, while, break
		
	4) 배열 → 일차배열
	5) 객체지향
		캡슐화, 상속, 다형성(오버라이딩) ===> 주코스!!!
		
	6) 메소드
		 -리턴형
		 -매개변수 : 사용자 요청한 값
	7) 예외처리
		 -에러복구 : try~catch
		 -에러피하기 : throws
		 -에러발생 : throw
	8) 라이브러리
		 java.util	:	List, Date, StringToken
	 	 java.io	:	File
		 java.sql	:	Connection, resultSet, execute
	9) 컬렉션
	========================================기본
	Thread / Network [모바일-컴퓨터와 모바일을 연결]
	object = finalize()소멸, clone()복제, system.gc()메모리회수,초기화 
		
	[6교시]
	init : 자동으로 호출 service : Thread함수
	
	하나의 객체를 갖고 계속하여 재사용해 나가는걸 싱글턴 ㅇ라고 함
	하나의 객체를 복사하여 메모리를 여러개 만들어 쓰는걸 프로토타입 이라고 함
	
	기술면접 
		에러가 두가지가있다
		CheckException			UnCheckException
		============			==============
		io, sql(컴파일익셉션)		(런타임,실행시익셉션) 
		반드시 예외처리를 함		감각적으로 하는것.. 예외처리 할지 말지~...
		javac						java
		
	Controller
	VO
	DAO 여러개...
	
	
	