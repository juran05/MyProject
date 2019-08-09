package com.sist.model;

import javax.servlet.http.HttpServletRequest;
import com.sist.dao.*;

public class InsertOkModel implements Model {
	@Override
	public String handlerRequest(HttpServletRequest request) throws Throwable {
		try {
			request.setCharacterEncoding("UTF-8");
		} catch (Exception ex) { }
		String name = request.getParameter("name");
		String subject = request.getParameter("subject");
		String content = request.getParameter("content");
		String pwd = request.getParameter("pwd");
		
		//VO로 묶기
		BoardVO vo = new BoardVO();
		vo.setName(name);
		vo.setSubject(subject);
		vo.setContent(content);
		vo.setPwd(pwd);
		
		//DAO로 전송
		BoardDAO.boardInsert(vo);
		
		return "redirect:list.do"; //인서트완료시 이쪽으로 이동!!
	}
}
