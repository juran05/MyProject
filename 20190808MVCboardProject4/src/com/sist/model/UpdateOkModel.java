package com.sist.model;

import javax.servlet.http.HttpServletRequest;

import com.sist.dao.BoardDAO;
import com.sist.dao.BoardVO;

public class UpdateOkModel implements Model {

	@Override
	public String handlerRequest(HttpServletRequest request) throws Throwable {

			try {
				request.setCharacterEncoding("UTF-8");
			} catch (Exception ex) {}
			// JSP update.jsp에서 .do로 보내준 데이터를 받기(어플리케이션-컨테스트가 연결해줌)
			String name=request.getParameter("name");
			String no=request.getParameter("no");
			String subject=request.getParameter("subject");
			String content=request.getParameter("content");
			String pwd=request.getParameter("pwd");
			
			// VO에 값묵기
			BoardVO vo = new BoardVO();
			vo.setName(name);
			vo.setNo(Integer.parseInt(no));
			vo.setSubject(subject);
			vo.setContent(content);
			vo.setPwd(pwd);

			// DB쪽으로 보내기
			boolean bCheck = BoardDAO.boardUpdate(vo);
			
			// bCheck를 JSP로 보내고 JSP가 처리하고 보내게 만들기
			request.setAttribute("bCheck", bCheck);
			
			// 조건걸기, 변경한 내용이 detail로 넘어가야 하므로 no가 필요하다
			if(bCheck==true) { 
				request.setAttribute("no", no);
			}
			return "boardview/update_ok.jsp";
		
	}

}
