package com.sist.model;

import javax.servlet.http.HttpServletRequest;
import com.sist.dao.*;

public class DeleteOkModel implements Model {

	@Override
	public String handlerRequest(HttpServletRequest request) throws Throwable {

		String no = request.getParameter("no");
		String pwd = request.getParameter("pwd");
		
		boolean bCheck = BoardDAO.boardDelete(Integer.parseInt(no), pwd);
		
		request.setAttribute("bCheck", bCheck);
		return "boardview/delete_ok.jsp";
		}
}
