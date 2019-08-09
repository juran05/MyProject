package com.sist.model;

import javax.servlet.http.HttpServletRequest;
import com.sist.dao.*;

public class UpdateModel implements Model {

	@Override
	public String handlerRequest(HttpServletRequest request) throws Throwable {
		// �����͹ޱ� (�Խù���ȣ)
		String no = request.getParameter("no");
		BoardVO vo = BoardDAO.boardUpdateData(Integer.parseInt(no));
		
		
		//�����ͺ�����] ������ ���Ǳ�
		request.setAttribute("vo", vo);
		
		//�����ͺ����� ] update.jsp�� ������Ʈ �߰��� �� vo�� ������, �� ������ �ؿ� �ƴ°��� ���ϴ�~
		return "boardview/update.jsp";
	}

}
