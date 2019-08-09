<!-- 

SQLPLUS 에서 ed test2
실행확인 : @test2

-- 2019.07.31 자료실(업로드, 다운로드)
CREATE TABLE databoard(
	no NUMBER,
	name VARCHAR2(34) CONSTRAINT db_name_nn NOT NULL,
	subject VARCHAR2(4000) CONSTRAINT db_sub_nn NOT NULL,
	content CLOB CONSTRAINT db_cont_nn NOT NULL,
	pwd VARCHAR2(10) CONSTRAINT db_pwd_nn NOT NULL,
	regdate DATE DEFAULT SYSDATE,
	hit NUMBER DEFAULT 0,
	filename VARCHAR2(260),
	filesize NUMBER,
	CONSTRAINT db_no_pk PRIMARY KEY(no)
);


CREATE TABLE databoardReply(
	no NUMBER,
	bno NUMBER,
	id VARCHAR2(20) CONSTRAINT dr_id_nn NOT NULL,
	name VARCHAR2(34) CONSTRAINT dr_name_nn NOT NULL,
	msg CLOB CONSTRAINT dr_msg_nn NOT NULL,
	regdate DATE DEFAULT SYSDATE,
	CONSTRAINT dr_no_pk PRIMARY KEY(no),
	CONSTRAINT dr_bno_fk FOREIGN KEY(bno)
	REFERENCES databoard(no) //참조키 
	
	
	...약식을쓰면 테이블 수정하기가 힘들고
	CONSTRAINT로 이름부여하고 들어가는게 편함...
	
	VO,DAO만들때 테이블에 따라서 VO는 두개가 될수도 있음!!! 
	
	
	*클래스 구분
	
	testVO
	testDAO
	(컴포넌트1) : 나중에 재사용을 위해 따로 따로 묶음
														testService : 옆에 두개의 컴포넌트를 통합함, DAO의 집합
	testreplyVO										(컨테이너)
	testreplyDAO
	(컴포넌트2) : 나중에 재사용을 위해 따로 따로 묶음
	
	
	VO,DAO 만들때
	Oracle 에는 ( ) 괄호안 자바<<< 
	문자형(String) CHAR(1~2000byte), VARCHAR2(1~4000byte), CLOB(4GB)
	
				↓																기술면접) i, g, c의 차이는?  i는 internet상에서 쿼리문장구현
				(9i버전 inputStream, 10g~String)	12c, 18c...											g는 grade 그래픽, sqldeveloper 편집기 가지고 만듦
																												c는 component
																				
	숫자형(int,double) NUMBER(10) or NUMBER(7,2) ←실수, 총 7자리 중 2자리를 소수로 씀
	날짜형(java.util.date) DATE,TIMESTAMP
	기타형-그림이애니메이션동영상(inputStream) BLOB(4GB-binary형식저장), BFILE(4GB-file형식저장)
	많이쓰임
	
	
	--단일뷰
/*
CREATE VIEW emp_view
AS SELECT * FROM emp;
*/

--디비안에 데이터값 채우기
INSERT INTO databoard VALUES(1,'보라돌이','파일업로드','파일업로드입니다','1234',SYSDATE,0,'',0);
INSERT INTO databoard VALUES(2,'뚜비','파일업로드','파일업로드입니다','1234',SYSDATE,0,'',0);
INSERT INTO databoard VALUES(3,'나나','파일업로드','파일업로드입니다','1234',SYSDATE,0,'',0);
INSERT INTO databoard VALUES(4,'스폰지밥','파일업로드','파일업로드입니다','1234',SYSDATE,0,'',0);
INSERT INTO databoard VALUES(5,'청소기','파일업로드','파일업로드입니다','1234',SYSDATE,0,'',0);
INSERT INTO databoard VALUES(6,'다람이','파일업로드','파일업로드입니다','1234',SYSDATE,0,'',0);
INSERT INTO databoard VALUES(7,'뚱이','파일업로드','파일업로드입니다','1234',SYSDATE,0,'',0);
INSERT INTO databoard VALUES(8,'보라돌이','파일업로드','파일업로드입니다','1234',SYSDATE,0,'',0);
INSERT INTO databoard VALUES(9,'텔레토비','파일업로드','파일업로드입니다','1234',SYSDATE,0,'',0);
INSERT INTO databoard VALUES(10,'둘리','파일업로드','파일업로드입니다','1234',SYSDATE,0,'',0);
INSERT INTO databoard VALUES(11,'또치','파일업로드','파일업로드입니다','1234',SYSDATE,0,'',0);

COMMIT;
); -->