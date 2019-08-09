마이바티스 연결...

[ ! ] MVC구조파악
1. 데이터 읽기 (Java) → Model
	====================== 전송을 하는 클래스 이용 (Servlet=Controller) 컨트롤러가 둘을 연결 시켜주는 애
2. 읽은 데이터 출력 (Jsp) → View

java -> jsp전송X 굳이 하고싶다면 한가지 방법 뿐..
			Jsp에서 Java의 메소드 호출... 근데 이렇게되면 MVC구조가 아니라 Model1(MV)방식 이다

java -> servlet(jsp) -> jsp -> Model -> Controller -> View Model2(MVC)방식
			
			▶항상 모델부터 시작한다 왜? 데이터를 먼저 갖고와서 뿌려야 하기때문에 자바(모델)이 먼저 수행한다
			모든 웹 프로그램에 들어가면 <<데이터읽기>>를 먼저하고 그 다음이 <<데이터출력>>이다
			
			
			업데이트나 딜리트는 no, 때문에 jsp뷰단에 hidden이 꼭 필요하다
			
			Jsp뷰에서 (.do)를 하면 모델로 넘어간다★
			
			application-context.xml에서 id가 JSP뷰 단에서 보내주는 이름(.do)이다
			그러면 Controller의 DispatcherServlet가 Class를 연결해줌 [ 중요! ]
			


====
항상 프로그램의 시작은 web.xml에서 시작한다
Tomcat이 컨트롤러 호출하기 전에 필요한 데이터를 넘기고 시작한다
web.xml파일들이 전부다 DispatcherServlet과 연결된다 

	메모리할당
	
	패스정함
	
	파싱
	
			
			
			xeb.xml에서 init이 디스패처서블릿의 맨우부분!! XML 파일경로 ,읽어드리는 부붑ㄴ
			init 에서 짧은 이름이 키고 경로명이 값이다.
			
			
[검색] 동적쿼리 이용(←마이바티스 핵심)
		
		
		깃허브
		1.팀에서 에드투 인덱스 플러스 커밋 앤 푸쉬
		2. 최신거 가져올때 풀
		3. 고친거 가져올때 커밋	
			