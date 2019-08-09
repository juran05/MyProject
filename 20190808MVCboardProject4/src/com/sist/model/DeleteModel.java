package com.sist.model;

import javax.servlet.http.HttpServletRequest;
import com.sist.dao.*;

public class DeleteModel implements Model {

	@Override
	public String handlerRequest(HttpServletRequest request) throws Throwable {
		String no = request.getParameter("no");
		request.setAttribute("no", no);	
		return "boardview/delete.jsp";
	}
}
