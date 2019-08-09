package com.sist.model;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import com.sist.dao.*;

public class FindModel implements Model {

	@Override
	public String handlerRequest(HttpServletRequest request) throws Throwable {
			
		try {
			request.setCharacterEncoding("UTF-8");
		} catch (Exception ex) { } // ���� �� ������ ��� printstack��¼�� �Ⱦ� 
		
			// DB���� �����͹ޱ�
				String fs = request.getParameter("fs");
				String ss = request.getParameter("ss");
	
			// DB�� �����´ٰ�
			// DAO�� �޴°� (BoardVO����) ������ �ޱ� ������ = VO�� ������
			// Map�ϳ� �������
				Map map = new HashMap();
				map.put("fs", fs); // WHERE ${fs} LIKE '%'||#{ss}||'%'
				map.put("ss", ss); // (Ű,��)
				
				List<BoardVO> list = BoardDAO.boardFindData(map);
				// ��� request.setAttribute ��������
				request.setAttribute("list", list);
				request.setAttribute("count", list.size()); //�˻� ����0�϶� ���̺� ���ϴ� �Ⱥ��̰� 
		return "boardview/find.jsp";
	}
}
