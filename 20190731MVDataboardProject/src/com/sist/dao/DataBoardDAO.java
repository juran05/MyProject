package com.sist.dao;
import java.util.*;
import java.sql.*;
public class DataBoardDAO {
	//★시작연결
	private Connection conn; //Socket첨부(연결기기) → JDBC는 정확한 전송을 하기위한 TCP프로그램
	private PreparedStatement ps; //InputStream(값을 읽어옴), OutputStream(SQL문장전송)
	private final String URL = "jdbc:oracle:thin:@localhost:1521:ORCL"; //ORCL 디비이름, 이안에 파일이 다 들어가있음
	// new Socket("ip", 포트번호) : 포트번호는 0~6553번 까지 사용가능 : 0~1024 => 1521,1433,7000,8080 은 자주쓰임, 있음
	
	//★드라이버등록
	//thin(연결만해주는 역활), oci(데이터를 갖다 놓고 시작한다 한단계 거치지 않아 속도가 빠르다는 장점!!! 근데 유료다ㅎ안씀ㅋ...)
	//한번만 등록해서 생성자(클래스랑이름같음)에서 연결~!!!
	public DataBoardDAO() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver"); //리플렉션, 메모리 할당을 함 new를 사용하지않음 결합성을 낮추기위해...
			// 클래스의 이름을 읽어서 클래스를 제어 → Spring
			// new를 사용하지 않고 메모리 할당을 할때 씀!!!
			// Class.forName 을 쓸려면 반드시 패키지명부터~클래스명까지 줘야함 
			// JSP 에서는 <jsp:useBean id=" " class="com.sist.dao.BoardDAO"> 를 사용하여 메모리할당을 했다
			// 1. 프로그램은 결합성(의존성)이 낮아야한다→유지보수를 위해!!! (스프링기반DI)
			// 2. 응집성이 강해야 한다→메소드는 한개의 기능만 수행이 가능하게 만든다!!! (스프링기반AOP)
		} catch (Exception ex) {
			ex.printStackTrace();
		} 
	}
	//★연결
		public void getConnection() {
			try {
				conn = DriverManager.getConnection(URL,"scott","tiger");
				// 오라클 => conn scott/ tiger	
			} catch (Exception ex) {
			}
		}
	//★해제	
		public void disConnection() {
			try {
				if(ps!=null) ps.close();
				if(conn!=null) conn.close();
				// 오라클 exit
			} catch (Exception ex) {
			}
		}
		
	//★기능
		public List<DataBoardVO> databoardListData(int page) {
			List<DataBoardVO> list = new ArrayList<DataBoardVO>();
			try {		
				//연결
				getConnection();
				//SQL문장 전송
				String sql="SELECT no,subject,name,regdate,hit,num "
							+"FROM(SELECT no,subject,name,regdate,hit,rownum as num "
							+"FROM(SELECT no,subject,name,regdate,hit "
							+"FROM databoard ORDER BY no DESC)) "
							+"WHERE num BETWEEN ? AND ?";
				/*
				 * View => 가상테이블, 종류가 세가지!!
				 *  =단일뷰 : 테이블 1개를 연결할때
				 *  =복합뷰 : 테이블 여러개를 연결할때(Join,SubQuery)
				 *  =인라인뷰(***) : 출력해놓고 출력한 위치 가져옴 *FROM~SELECT가 붙음
				 */
				// 쿼리문장실행하기위해 sql문 문장전송
				ps = conn.prepareStatement(sql);
				// ?물음표에 값채워서 실행요청 후 
				int rowSize=10;
				int start=(rowSize*page)-(rowSize-1); // -1을 하는이유 : rownum이 1부터 시작하기 때문에...
				int end = rowSize*page;		
				
				ps.setInt(1, start); //1번째 물음표
				ps.setInt(2, end); //2번재 물음표
				//결과 값을 읽어온다
				ResultSet rs = ps.executeQuery();
				while(rs.next()) {
					DataBoardVO vo = new DataBoardVO();
					vo.setNo(rs.getInt(1));
					vo.setSubject(rs.getString(2));
					vo.setName(rs.getString(3));
					vo.setRegdate(rs.getDate(4));
					vo.setHit(rs.getInt(5));
					
					list.add(vo);
				}
				rs.close();//결과값 읽어온거 while문 끝나면 닫기
				
			} catch (Exception ex) {
				ex.printStackTrace();
			} finally {
				disConnection();
			}
			return list;
		}
		
		//---------- 데이터 추가 하는부분
/*		no NUMBER,
		name VARCHAR2(34) CONSTRAINT db_name_nn NOT NULL,
		subject VARCHAR2(4000) CONSTRAINT db_sub_nn NOT NULL,
		content CLOB CONSTRAINT db_cont_nn NOT NULL,
		pwd VARCHAR2(10) CONSTRAINT db_pwd_nn NOT NULL,
		regdate DATE DEFAULT SYSDATE, 
						=========
		hit NUMBER DEFAULT 0,
						=========
		filename VARCHAR2(260),
		filesize NUMBER,
		CONSTRAINT db_no_pk PRIMARY KEY(no)
	);*/
		public void databoardInsert(DataBoardVO vo) {
			try {
				getConnection();
				String sql="INSERT INTO databoard(no,name,subject,content,pwd,filename,filesize) "
							+"VALUES((SELECT NVL(MAX(no)+1,1) FROM databoard),?,?,?,?,?,?)"; //시퀀스 자동증가를 안해줬기 때문에 널값일때 1로 초기화를 넣고간다!!
				//reg랑 hit는 디폴트를 걸었기 때문에 밑에 sql문에 안들어가도된다.. 지금처럼 컬러명을 뺴고 인서트인투 해줘야 자동으로 인식됌
				
				ps=conn.prepareStatement(sql);
				ps.setString(1, vo.getName());
				ps.setString(2, vo.getSubject());
				ps.setString(3, vo.getContent());
				ps.setString(4, vo.getPwd());
				ps.setString(5, vo.getFilename());
				ps.setInt(6, vo.getFilesize());
				
				ps.executeUpdate(); //인서트니까 업데이트...
				
			} catch (Exception ex) {
				ex.printStackTrace();
			} finally {
				disConnection();
			}
		}
		//detail.jsp와 연관... 상세보기 부분 .. 모든 데이터 가져오기 
		public DataBoardVO databoardDetailData(int no) {
			DataBoardVO vo = new DataBoardVO();
			
			try {
				getConnection();
				String sql="UPDATE databoard SET "
							+"hit=hit+1 "
							+"WHERE no=?";
				ps=conn.prepareStatement(sql);
				ps.setInt(1, no);
				ps.executeUpdate(); //조회수 증가
				
				sql="SELECT no,name,subject,content,regdate,hit,filename,filesize "
					+"FROM databoard "
					+"WHERE no=?";
				ps=conn.prepareStatement(sql);
				ps.setInt(1, no);
				
				ResultSet rs = ps.executeQuery(); //상세보기에 출력할 결과값 가져오기
				
				rs.next();
				
				vo.setNo(rs.getInt(1));
				vo.setName(rs.getString(2));
				vo.setSubject(rs.getString(3));
				vo.setContent(rs.getString(4));
				vo.setRegdate(rs.getDate(5));
				vo.setHit(rs.getInt(6));
				vo.setFilename(rs.getString(7));
				vo.setFilesize(rs.getInt(8));
				
				rs.close();
				
			} catch (Exception ex) {
				ex.printStackTrace();
			} finally {
				disConnection();
			}	
			return vo;
		}
		//list.jpg 연관 ...총페이지 만들기
				public int databoardTotalPage() {
					int total=0;
					try {
						getConnection();
						String sql="SELECT CEIL(COUNT(*)/10.0) FROM databoard"; // 10.0으로 나눈걸 올림해줌, 총페이지는 올려야하니까
						ps=conn.prepareStatement(sql);
						ResultSet rs = ps.executeQuery();
						rs.next();
						total=rs.getInt(1);
						rs.close();
						// CEIL, ROUND, TRUNC(버림)
						
					} catch (Exception ex) {
						ex.printStackTrace();
					} finally {
						disConnection();
					}
					return total;
				}
		
		
		public int updateDate() {
			int total=0;
			try {
				getConnection();
				String sql="SELECT CEIL(COUNT(*)/10.0) FROM databoard"; // 10.0으로 나눈걸 올림해줌, 총페이지는 올려야하니까
				ps=conn.prepareStatement(sql);
				ResultSet rs = ps.executeQuery();
				rs.next();
				total=rs.getInt(1);
				rs.close();
				// CEIL, ROUND, TRUNC(버림)
				
			} catch (Exception ex) {
				ex.printStackTrace();
			} finally {
				disConnection();
			}
			return total;
		}
		//4. DAO만들기 0801
		public DataBoardVO databoardUpdateData(int no)
		{
			DataBoardVO vo=new DataBoardVO();
			try
			{
				getConnection();
				String sql="SELECT no,name,subject,content "
				   +"FROM databoard "
				   +"WHERE no=?";
				ps=conn.prepareStatement(sql);
				ps.setInt(1, no);
				ResultSet rs=ps.executeQuery();
				rs.next();
				vo.setNo(rs.getInt(1));
				vo.setName(rs.getString(2));
				vo.setSubject(rs.getString(3));
				vo.setContent(rs.getString(4));
				rs.close();
				
			}catch(Exception ex)
			{
				ex.printStackTrace();
			}
			finally
			{
				disConnection();
			}
			return vo;
		}
		// 원래는 JSP에서 DAO를 호출했늗네 이제는 DAO를 Model에서 호출하고 결과값을 JSP로 보냄
		// model 업데이트 오케이 를 처리
		public boolean databoardUpdate(DataBoardVO vo) {
			boolean bCheck=false;
			
			try {
				getConnection();
				//비밀번호검색 맞으면 수정 틀리면 수정안함
				String sql="SELECT pwd FROM databoard "
							+"WHERE no=?";
				ps=conn.prepareStatement(sql);
				ps.setInt(1, vo.getNo());
				ResultSet rs = ps.executeQuery();
				rs.next();
				String db_pwd = rs.getString(1);
				rs.close();
				
				if(db_pwd.equals(vo.getPwd())) {
					bCheck=true; //실제 수정을 해줘야 하는 부분
					sql="UPDATE databoard SET "
						+"name=?, subject=?, content=? "
						+"WHERE no=?";
					ps=conn.prepareStatement(sql);
					ps.setString(1,vo.getName());
					ps.setString(2,vo.getSubject());
					ps.setString(3,vo.getContent());
					ps.setInt(4,vo.getNo());
					
					ps.executeUpdate();
					
				} else {
					bCheck=false; //수정X
				}
				
			} catch (Exception ex) {
				ex.printStackTrace();
			} finally {
				disConnection();
			}
			System.out.println("daobcheck:" + bCheck);
			return bCheck;
		}
		//login_ok가 됐을 경우 login 처리 하는 곳
		public String isLogin(String id, String pwd) {
			String result="";
			try {
				getConnection();
				String sql="SELECT COUNT(*) FROM member WHERE id=?";
				ps=conn.prepareStatement(sql);
				ps.setString(1, id);
				
				ResultSet rs = ps.executeQuery();
				
				rs.next();
				int count=rs.getInt(1);
				rs.close();
				
				if(count==0) { 
					result="NOID";
				} else {
					sql="SELECT pwd,name FROM member WHERE id=?";
					ps=conn.prepareStatement(sql);
					ps.setString(1, id);
					rs=ps.executeQuery();
					rs.next();
					String db_pwd=rs.getString(1);
					String name=rs.getString(2);
					rs.close();
					
					if(db_pwd.equals(pwd))
					{
						result=name;
					}
					else
					{
						result="NOPWD";
					}
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			} finally {
				disConnection();
			}
			return result;
		}
		
		// 댓글
		public List<DataBoardReplyVO> databoardReplyData(int bno)
		{
			List<DataBoardReplyVO> list=new ArrayList<DataBoardReplyVO>();
			try
			{
				getConnection();
				String sql="SELECT no,bno,id,name,msg,TO_CHAR(regdate,'YYYY-MM-DD HH24:MI:SS') "
						  +"FROM databoardReply "
						  +"WHERE bno=? "
						  + "ORDER BY no DESC";
				ps=conn.prepareStatement(sql);
				ps.setInt(1, bno);
				ResultSet rs=ps.executeQuery();
				while(rs.next())
				{
					DataBoardReplyVO vo=new DataBoardReplyVO();
					vo.setNo(rs.getInt(1));
					vo.setBno(rs.getInt(2));
					vo.setId(rs.getString(3));
					vo.setName(rs.getString(4));
					vo.setMsg(rs.getString(5));
					vo.setDbday(rs.getString(6));
					list.add(vo);
				}
				rs.close();
			}catch(Exception ex)
			{
				ex.printStackTrace();
			}
			finally
			{
				disConnection();
			}
			return list;
		}
		// 댓글 달기
		public void replyInsert(DataBoardReplyVO vo) {
			try {
				//연결
				getConnection();
				String sql="INSERT INTO databoardReply VALUES("
						+ "(SELECT NVL(MAX(no)+1,1) FROM databoardReply), " //자동증가 때문에 번호 맞춰 줄려고...
						+ "?,?,?,?,SYSDATE)";
						//no,bno,id,name,msg,sysdate
						ps = conn.prepareStatement(sql);
						ps.setInt(1, vo.getBno());
						ps.setString(2, vo.getId());
						ps.setString(3, vo.getName());
						ps.setString(4, vo.getMsg());
						ps.executeUpdate();
						
			} catch (Exception ex) {
				ex.printStackTrace();
			} finally {
				disConnection();
			}
		}
		//댓글 삭제
		public void replyDelete(int no) {
			try {
				getConnection();
				String sql="DELETE FROM databoardReply WHERE no=?";
				ps=conn.prepareStatement(sql);
				ps.setInt(1, no);
				ps.executeUpdate();
			} catch (Exception ex) {
				ex.printStackTrace();
			} finally {
				disConnection();
			}
		}
		//댓글 수정
		public void replyUpdate(int no, String msg) {
			try {
				getConnection();
				String sql="UPDATE databoardReply SET msg=? WHERE no=?";
				ps=conn.prepareStatement(sql);
				ps.setString(1, msg);
				ps.setInt(2, no);
				
				ps.executeUpdate();
				
			} catch (Exception ex) {
				ex.printStackTrace();
			} finally {
				disConnection();
			}
		}
	}

