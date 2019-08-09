package com.sist.model;

import javax.servlet.http.HttpServletRequest;
import com.sist.dao.*;

public class DetailModel implements Model {

	@Override
	public String handlerRequest(HttpServletRequest request) throws Throwable {
		//request.setAttribute("msg", "게시판 내용보기"); 오전
		String no = request.getParameter("no");
		BoardVO vo = BoardDAO.boardDetailData(Integer.parseInt(no));
		request.setAttribute("vo", vo);
		
		return "boardview/detail.jsp";
	}

}
