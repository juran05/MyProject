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
	public void databoardList_list(HttpServletRequest request) { //�� �Լ��� ����Ʈ.JSP�� �־�� �ϴµ� Model.java�� ������, �и��ϱ�����
		//list.jsp�� �������� ���� �ڹپȿ��� ó��
		String page=request.getParameter("page");
		if(page==null) 
			page="1";
		int curpage=Integer.parseInt(page); //��
		//�������б�
		DataBoardDAO dao = new DataBoardDAO();
		List<DataBoardVO> list = dao.databoardListData(curpage); //��
		
		//------
		int totalpage=dao.databoardTotalPage();
		//------
		
		//jsp�� ����� ����
		request.setAttribute("curpage", curpage); //(Ű,��)
		request.setAttribute("list", list);
		//------
		request.setAttribute("totalpage", totalpage);
		//------
	}
	
	//insert_ok ���� �Խ��ϴ�...
	public void databoard_insert_ok(HttpServletRequest request, HttpServletResponse response) { //����� ��û���� �޾ƾ� �ϹǷ� �ȿ� �Լ����ֱ�
								//�������� �ޱ����� request , �̵��ϱ����� response
		try {
			request.setCharacterEncoding("UTF-8"); //�ѱۺ�ȯ�ڵ�� ����ó��...
			String path="c:\\upload";
			String enctype="UTF-8";
			int size=100*1024*1024; //�ִ������� �����ִ� ���� ũ�Ⱑ 100�ް�(1024KB, 1024*1024KB=MB)
			MultipartRequest mr = new MultipartRequest(request, path, size, enctype, new DefaultFileRenamePolicy()); //���Ͼ��ε�ÿ� (������Ʈ,��������)
																			//new DefaultFileRenamePolicy() ���� ������ ������ �ڵ����� �̸�+1 ��ȯ
			
			String name=mr.getParameter("name");
			String subject=mr.getParameter("subject");
			String content=mr.getParameter("content");
			String pwd=mr.getParameter("pwd");
			
			DataBoardVO vo = new DataBoardVO();
			
			vo.setName(name);
			vo.setSubject(subject);
			vo.setContent(content);
			vo.setPwd(pwd); // �긦 ��� DB������ �Ѱ�����Ѵ�
			
			String filename=mr.getOriginalFileName("upload");
			
			if(filename==null) { //������ ���� ������ ���ε带 ���� ����
				vo.setFilename("");
				vo.setFilesize(0);
			} else { //������ �ö�� ����
				File file = new File(path+"\\"+filename);
				vo.setFilename(filename);
				vo.setFilesize((int)file.length());
			}
			DataBoardDAO dao = new DataBoardDAO();
			dao.databoardInsert(vo);
		} catch (Exception ex) {
		
		}
		
	}
	//DAO�� �󼼺��� �� detail.jsp����
	public void databoard_detail(HttpServletRequest request) {
		//detail.jsp?no=${vo.no }	�ڹٿ� �и��ϱ����� % ���� �κ� ����� �ڵ�
		String no = request.getParameter("no");
		String curpage = request.getParameter("page");
		//DAO����
		DataBoardDAO dao = new DataBoardDAO();
		DataBoardVO vo = dao.databoardDetailData(Integer.parseInt(no));
		//���������� �� JSP ����
		request.setAttribute("vo", vo); //detail.jsp���� �긦 ȣ���Ѵ�... ������ vo�� �޴´�
		request.setAttribute("curpage", curpage);
		
		//-----0801 ���
		
		List<DataBoardReplyVO> list = dao.databoardReplyData(Integer.parseInt(no));
		request.setAttribute("list", list);
		request.setAttribute("len", list.size());
		
		//-----
		
	}
	// update 0801
    public void databoard_update_data(HttpServletRequest request) {//detail���� no�� ���� �Ƿ����� ���� request�� �����ִ�
		//<a href="update0801.jsp?=no${vo.no }" class="btn btn-sm btn-success">����</a> 0801�� �����������°��� ��
		
    	//jsp���� ���� ������
    	String no=request.getParameter("no");
    	String page=request.getParameter("page");
		
		// DAO����
		// request�� ���� ����ش� �� JSP���� request�� �ִ� �����͸� ���
		// �̿� ���� ����� �����ϴ°� *Model[java] : ��û ó��
		// *Bean[vo] : ����� ���� �����Ϳ�, �б⾲�� ��������(getter,setter)
		// *View[jsp] : ȭ�����
		// 3-DAO�� ���ÿ�.
		
		// 5 Data��������
    	DataBoardDAO dao=new DataBoardDAO();
    	DataBoardVO vo=dao.databoardUpdateData(Integer.parseInt(no));	

    	request.setAttribute("vo", vo); // setAttribute ���ʿ��� �����͸� �߰��Ҷ� ���°�,...
    	request.setAttribute("curpage", page);
    	//�¾�Ʈ����Ʈ�� ����Ҷ� Ű����, ������Ʈ���� �̸��ؿ� ������ �������� �����	
	}
    public void databoard_update_ok(HttpServletRequest request) {
    	try {
			request.setCharacterEncoding("UTF-8");
			
		} catch (Exception ex) {
		}
			
			//jsp���� ���� ������
			String name=request.getParameter("name");
			String subject=request.getParameter("subject");
			String content=request.getParameter("content");
			String pwd=request.getParameter("pwd");
			String no=request.getParameter("no");
			String page=request.getParameter("page");
			
			DataBoardVO vo = new DataBoardVO();
			
			// �� ���� DAO�� ����, DAO���� ó�����Ǹ� �� ������� �����ͼ� update_ok0801.jsp�� �����Ұ�
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
	    	// loginok ���� �� �޾ƿ���
	    	String id=request.getParameter("id");
	    	String pwd=request.getParameter("pwd");
	    	// DAO���� 
	    	DataBoardDAO dao=new DataBoardDAO();
	    	String result=dao.isLogin(id, pwd); //����� �޾ƿ���
	    	
	    	if(!(result.equals("NOID")||result.equals("NOPWD"))) {
	    		// ������ �޶����� session�� �����ϱ�.. ���α׷��� �����Ҷ����� id�� pwd�� ����ؾ� �ϹǷ�...
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
    	
    	// request �� ���ؼ� session�̳� cookie�� ���� �� �� ����
    	String id = (String)session.getAttribute("id"); //getAttribute �� return���� Object�� ������ String ���� ������
    	String name = (String)session.getAttribute("name");
    	
    	DataBoardReplyVO vo = new DataBoardReplyVO();
    	vo.setId(id);
    	vo.setName(name);
    	vo.setBno(Integer.parseInt(bno));
    	vo.setMsg(msg);
    	
    	// DAO�� ���� �� ����Ŭ ����
    	// DAO���� �μ�Ʈ�� ��������
    	DataBoardDAO dao = new DataBoardDAO();
    	dao.replyInsert(vo);
    	
    	// �����Ϸ� �̵��� �ϱ� ���ؼ��� �ʿ��� �����͸� �����������... bno(�Խù���ȣ)�� page(����������)�� �̰� ������Ʈ�� ���� ����
    	
    	request.setAttribute("bno", bno);
    	request.setAttribute("page", page);
    }
    
	// ��ۻ���
    public void reply_delete(HttpServletRequest request) {
    	String no =request.getParameter("no"); // ����ǹ�ȣ
    	String bno =request.getParameter("bno");
    	String page = request.getParameter("page");
    	
    	//DAO�� ���� ����
    	DataBoardDAO dao = new DataBoardDAO();
    	dao.replyDelete(Integer.parseInt(no));
    	
    	//JSP�� �ʿ��� ������ ����
    	request.setAttribute("no", bno); // �Խñ��� ��ȣ�� ������
    	request.setAttribute("page", page);
    }
    // ��ۼ���
    public void reply_update(HttpServletRequest request) {
    	
    	try {
    		request.setCharacterEncoding("UTF-8");
		} catch (Exception ex) {
			
		}
    	String no =request.getParameter("no"); // ����ǹ�ȣ
    	String bno =request.getParameter("bno");
    	String page = request.getParameter("page");
    	String msg = request.getParameter("msg");
    	
    	//DAO�� ���� ����
    	DataBoardDAO dao = new DataBoardDAO();
    	dao.replyUpdate(Integer.parseInt(no), msg);
    	
    	//JSP�� �ʿ��� ������ ����
    	request.setAttribute("no", bno); // �Խñ��� ��ȣ�� ������
    	request.setAttribute("page", page);
    }
}
	