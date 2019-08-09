package com.sist.dao;
import java.io.*;
import java.util.*;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class BoardDAO {
	private static SqlSessionFactory ssf;
	static {
		// 초기화 블럭→ XML을 파싱 6
		try {
		// XML읽기
			Reader reader = Resources.getResourceAsReader("Config.xml");
			ssf = new SqlSessionFactoryBuilder().build(reader);
			
					
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	// 목록 출력
	public static List<BoardVO> boardListData(Map map) {
		List<BoardVO> list = new ArrayList<BoardVO>();
	
		// list에 값만 받음 된다
		// DBCP => 열고 닫는걸 해줘야한다 즉, 반환을 해야한다. 기본 커넥션의 최대개수) maxActive=8, maxIdle=8
		// DBCP는 반환하지 않으면 사용할 수 없기 때문에...
		SqlSession session = ssf.openSession(); // getConnection();
		list = session.selectList("boardListData",map);
		session.close(); // disConnection();  ←반환 하는 과정... 반환하지 않았는데 여러명이 들어가면 멈춰버린다
		return list;
	}
	// 에러가 null이라고 뜨는걸 방지하고자 try~catch 써준다
	public static void boardInsert(BoardVO vo) {
		SqlSession session = ssf.openSession(true); // ★아니믄 괄호안에 true 써주기 이러면 지절로 autoCommit이 된다
		session.insert("boardInsert",vo);
		//session.commit();// openSession은 autoCommit이 아니라서 insert끝나고나면 무조건 commit 써주기 
		session.close();
	}
	static public BoardVO boardDetailData(int no) {
		BoardVO vo = new BoardVO();
		
		SqlSession session = ssf.openSession();
		// 조회수증가
		session.update("hitIncrement",no);
		session.commit(); // ★커밋날리기~ ↓ 밑에는 안날려도 되니까 위에다가 true주면 안됌,한번만 수행해야하는데 true를 주면 commit 두번수행되니까...
		vo=session.selectOne("boardDetailData",no);
		//selectList(아이디명칭) 는 List로 받을때(VO여러개를 묶음)
		//selectOne(아이디명칭) 은 VO하나만 받을때
		session.close();
		
		return vo;
	}
	// 총페이지 
	   public static int boardTotalPage()
	   {
		   int total=0;
		   // 오라클 연결
		   SqlSession session=ssf.openSession();
		   total=session.selectOne("boardTotalPage");
		   // int a=new Object()
		   //                        id명   
		   // session.selectOne("boardDetailData", no);
		   //                        id           === parameterType
		   // 반환 
		   session.close();
		   return total;
	   }
	   // 수정데이터 읽기
	   // <select id="boardDetailData" resultType="BoardVO" parameterType="int">
	   public static BoardVO boardUpdateData(int no)
	   {
		   BoardVO list=new BoardVO();
		   SqlSession session=ssf.openSession();// getConnection()
		   list=session.selectOne("boardDetailData",no);// 재사용 
		   session.close();//disConnection(); 
		   return list;
	   }
	   // 수정하기
	   // <update id="boardUpdate" parameterType="BoardVO">
	   public static boolean boardUpdate(BoardVO vo)
	   {
		   boolean bCheck=false;
		   SqlSession session=ssf.openSession();
		   // DB에 있는 비밀번호 읽기 
		   String db_pwd=session.selectOne("boardGetPwd", vo.getNo());
		   if(db_pwd.equals(vo.getPwd()))
		   {
			   bCheck=true;
			   session.update("boardUpdate",vo);
			   session.commit();
		   }
		   session.close();
		   return bCheck;
	   }
	   // 삭제하기
	   // <delete id="boardDelete" parameterType="int">
	   public static boolean boardDelete(int no,String pwd)
	   {
		   boolean bCheck=false;
		   SqlSession session=ssf.openSession();
		   // DB에 있는 비밀번호 읽기 
		   String db_pwd=session.selectOne("boardGetPwd", no);
		   if(db_pwd.equals(pwd))
		   {
			   bCheck=true;
			   session.delete("boardDelete",no);
			   session.commit();
		   }
		   session.close();
		   return bCheck;
	   }
	   // 찾기 
	   // <select id="boardFindData" resultType="BoardVO" parameterType="java.util.Map">
	   public static List<BoardVO> boardFindData(Map map)
	   {
		   List<BoardVO> list=new ArrayList<BoardVO>();
		   SqlSession session=ssf.openSession();
		   list=session.selectList("boardFindData",map);
		   session.close();
		   return list;
	   }
	}
