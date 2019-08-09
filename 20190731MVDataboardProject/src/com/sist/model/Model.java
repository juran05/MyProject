package com.sist.model;
import java.io.*;
import java.util.*;
import java.net.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import com.sist.dao.*;

public class Model {
	public void databoardList_list(HttpServletRequest request) { //이 함수가 리스트.JSP에 있어야 하는데 Model.java로 뺀것임, 분리하기위해
		//list.jsp에 있을곳을 여기 자바안에서 처리
		String page=request.getParameter("page");
		if(page==null) 
			page="1";
		int curpage=Integer.parseInt(page); //★
		//데이터읽기
		DataBoardDAO dao = new DataBoardDAO();
		List<DataBoardVO> list = dao.databoardListData(curpage); //★
		
		//------
		int totalpage=dao.databoardTotalPage();
		//------
		
		//jsp에 결과값 전송
		request.setAttribute("curpage", curpage); //(키,값)
		request.setAttribute("list", list);
		//------
		request.setAttribute("totalpage", totalpage);
		//------
	}
	
	//insert_ok 에서 왔습니다...
	public void databoard_insert_ok(HttpServletRequest request, HttpServletResponse response) { //사용자 요청값을 받아야 하므로 안에 함수써주기
								//값보낸걸 받기위해 request , 이동하기위해 response
		try {
			request.setCharacterEncoding("UTF-8"); //한글변환코드라 예외처리...
			String path="c:\\upload";
			String enctype="UTF-8";
			int size=100*1024*1024; //최대한으로 들어갈수있는 파일 크기가 100메가(1024KB, 1024*1024KB=MB)
			MultipartRequest mr = new MultipartRequest(request, path, size, enctype, new DefaultFileRenamePolicy()); //파일업로드시에 (리퀘스트,저장폴더)
																			//new DefaultFileRenamePolicy() 같은 파일이 들어오면 자동으로 이름+1 변환
			
			String name=mr.getParameter("name");
			String subject=mr.getParameter("subject");
			String content=mr.getParameter("content");
			String pwd=mr.getParameter("pwd");
			
			DataBoardVO vo = new DataBoardVO();
			
			vo.setName(name);
			vo.setSubject(subject);
			vo.setContent(content);
			vo.setPwd(pwd); // 얘를 묶어서 DB쪽으로 넘겨줘야한다
			
			String filename=mr.getOriginalFileName("upload");
			
			if(filename==null) { //파일이 없기 때문에 업로드를 안한 상태
				vo.setFilename("");
				vo.setFilesize(0);
			} else { //파일이 올라온 상태
				File file = new File(path+"\\"+filename);
				vo.setFilename(filename);
				vo.setFilesize((int)file.length());
			}
			DataBoardDAO dao = new DataBoardDAO();
			dao.databoardInsert(vo);
		} catch (Exception ex) {
		
		}
		
	}
	//DAO의 상세보기 와 detail.jsp연관
	public void databoard_detail(HttpServletRequest request) {
		//detail.jsp?no=${vo.no }	자바와 분리하기위해 % 들어가는 부분 여기다 코딩
		String no = request.getParameter("no");
		String curpage = request.getParameter("page");
		//DAO연결
		DataBoardDAO dao = new DataBoardDAO();
		DataBoardVO vo = dao.databoardDetailData(Integer.parseInt(no));
		//받은데이터 → JSP 전송
		request.setAttribute("vo", vo); //detail.jsp에서 얘를 호출한다... 여기의 vo를 받는다
		request.setAttribute("curpage", curpage);
		
		//-----0801 댓글
		
		List<DataBoardReplyVO> list = dao.databoardReplyData(Integer.parseInt(no));
		request.setAttribute("list", list);
		request.setAttribute("len", list.size());
		
		//-----
		
	}
	// update 0801
    public void databoard_update_data(HttpServletRequest request) {//detail에서 no에 값을 실려보낸 값을 request가 갖고있다
		//<a href="update0801.jsp?=no${vo.no }" class="btn btn-sm btn-success">수정</a> 0801은 스쳐지나가는곳일 뿐
		
    	//jsp에서 받은 데이터
    	String no=request.getParameter("no");
    	String page=request.getParameter("page");
		
		// DAO연동
		// request에 값을 담아준다 → JSP에서 request에 있는 데이터를 출력
		// 이와 같이 기능을 수행하는걸 *Model[java] : 요청 처리
		// *Bean[vo] : 사용자 정의 데이터용, 읽기쓰기 변수설정(getter,setter)
		// *View[jsp] : 화면출력
		// 3-DAO로 가시오.
		
		// 5 Data가져오기
    	DataBoardDAO dao=new DataBoardDAO();
    	DataBoardVO vo=dao.databoardUpdateData(Integer.parseInt(no));	

    	request.setAttribute("vo", vo); // setAttribute 더필요한 데이터를 추가할때 쓰는것,...
    	request.setAttribute("curpage", page);
    	//셋어트리부트는 출력할때 키값임, 업데이트에서 이름밑에 씨유알 페이지로 출력함	
	}
    public void databoard_update_ok(HttpServletRequest request) {
    	try {
			request.setCharacterEncoding("UTF-8");
			
		} catch (Exception ex) {
		}
			
			//jsp에서 받은 데이터
			String name=request.getParameter("name");
			String subject=request.getParameter("subject");
			String content=request.getParameter("content");
			String pwd=request.getParameter("pwd");
			String no=request.getParameter("no");
			String page=request.getParameter("page");
			
			DataBoardVO vo = new DataBoardVO();
			
			// 이 값은 DAO로 전송, DAO에서 처리가되면 그 결과값을 가져와서 update_ok0801.jsp로 전송할것
			vo.setNo(Integer.parseInt(no));
			vo.setName(name);
			vo.setSubject(subject);
			vo.setContent(content);
			vo.setPwd(pwd); 
			
			DataBoardDAO dao = new DataBoardDAO();
			boolean bCheck = dao.databoardUpdate(vo);
			System.out.println("model bcheck:"+bCheck);
			request.setAttribute("bCheck", bCheck);
			request.setAttribute("page", page);
			request.setAttribute("no", no);
    }
    public void login(HttpServletRequest request) {
	    	// loginok 에서 값 받아오기
	    	String id=request.getParameter("id");
	    	String pwd=request.getParameter("pwd");
	    	// DAO연결 
	    	DataBoardDAO dao=new DataBoardDAO();
	    	String result=dao.isLogin(id, pwd); //결과값 받아왔음
	    	
	    	if(!(result.equals("NOID")||result.equals("NOPWD"))) {
	    		// 파일이 달라져서 session에 저장하기.. 프로그램이 종료할때까지 id와 pwd를 기억해야 하므로...
	    		HttpSession session=request.getSession();
	    		session.setAttribute("id", id);
	    		session.setAttribute("name", result);
	    	}
	    	
	    	request.setAttribute("result", result);
	    }
    public void reply_insert(HttpServletRequest request) {
    	try {
			request.setCharacterEncoding("UTF-8");
		} catch (Exception ex) { }
    	
    	String msg = request.getParameter("msg");
    	String bno = request.getParameter("bno");
    	String page = request.getParameter("page");
    	
    	HttpSession session = request.getSession();
    	
    	// request 를 통해서 session이나 cookie를 가져 올 수 있음
    	String id = (String)session.getAttribute("id"); //getAttribute 는 return형이 Object라 리턴형 String 으로 맞춰줌
    	String name = (String)session.getAttribute("name");
    	
    	DataBoardReplyVO vo = new DataBoardReplyVO();
    	vo.setId(id);
    	vo.setName(name);
    	vo.setBno(Integer.parseInt(bno));
    	vo.setMsg(msg);
    	
    	// DAO로 전송 → 오라클 연결
    	// DAO에서 인서트문 만들고오기
    	DataBoardDAO dao = new DataBoardDAO();
    	dao.replyInsert(vo);
    	
    	// 디테일로 이동을 하기 위해서는 필요한 데이터를 전송해줘야함... bno(게시물번호)와 page(현재페이지)로 이걸 리퀘스트에 실을 것임
    	
    	request.setAttribute("bno", bno);
    	request.setAttribute("page", page);
    }
    
	// 댓글삭제
    public void reply_delete(HttpServletRequest request) {
    	String no =request.getParameter("no"); // 댓글의번호
    	String bno =request.getParameter("bno");
    	String page = request.getParameter("page");
    	
    	//DAO로 만들어서 삭제
    	DataBoardDAO dao = new DataBoardDAO();
    	dao.replyDelete(Integer.parseInt(no));
    	
    	//JSP로 필요한 데이터 전송
    	request.setAttribute("no", bno); // 게시글의 번호가 들어가야함
    	request.setAttribute("page", page);
    }
    // 댓글수정
    public void reply_update(HttpServletRequest request) {
    	
    	try {
    		request.setCharacterEncoding("UTF-8");
		} catch (Exception ex) {
			
		}
    	String no =request.getParameter("no"); // 댓글의번호
    	String bno =request.getParameter("bno");
    	String page = request.getParameter("page");
    	String msg = request.getParameter("msg");
    	
    	//DAO로 만들어서 수정
    	DataBoardDAO dao = new DataBoardDAO();
    	dao.replyUpdate(Integer.parseInt(no), msg);
    	
    	//JSP로 필요한 데이터 전송
    	request.setAttribute("no", bno); // 게시글의 번호가 들어가야함
    	request.setAttribute("page", page);
    }
}
	