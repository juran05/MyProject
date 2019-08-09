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
			// JSP update.jsp���� .do�� ������ �����͸� �ޱ�(���ø����̼�-���׽�Ʈ�� ��������)
			String name=request.getParameter("name");
			String no=request.getParameter("no");
			String subject=request.getParameter("subject");
			String content=request.getParameter("content");
			String pwd=request.getParameter("pwd");
			
			// VO�� ������
			BoardVO vo = new BoardVO();
			vo.setName(name);
			vo.setNo(Integer.parseInt(no));
			vo.setSubject(subject);
			vo.setContent(content);
			vo.setPwd(pwd);

			// DB������ ������
			boolean bCheck = BoardDAO.boardUpdate(vo);
			
			// bCheck�� JSP�� ������ JSP�� ó���ϰ� ������ �����
			request.setAttribute("bCheck", bCheck);
			
			// ���ǰɱ�, ������ ������ detail�� �Ѿ�� �ϹǷ� no�� �ʿ��ϴ�
			if(bCheck==true) { 
				request.setAttribute("no", no);
			}
			return "boardview/update_ok.jsp";
		
	}

}
