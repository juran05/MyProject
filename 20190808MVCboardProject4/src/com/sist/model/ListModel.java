package com.sist.model;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import com.sist.dao.*;

public class ListModel implements Model {

	@Override
	public String handlerRequest(HttpServletRequest request) throws Throwable {
		//request.setAttribute("msg", "�Խ��� ���"); ����
		
		//��Ϲޱ�
		//�������ޱ�
		String page = request.getParameter("page");
		if(page==null) { // �������ڸ��� null�̶� page�� 1�� �������ְ� �����Ѵ�
			page="1";
		} 
		int curpage = Integer.parseInt(page);
		int rowSize = 10;
		int start = (curpage*rowSize)-(rowSize-1);
		int end = curpage*rowSize;
		
		//map�� ����
		Map map = new HashMap();
		map.put("start", start); // Ű���� board-mapper.xml �� #{start} AND #{end} ���� ��ġ�ؾ���
		map.put("end", end);
		
		List<BoardVO> list = BoardDAO.boardListData(map);
		//list.jsp ����
		request.setAttribute("list", list);
		
		return "boardview/list.jsp";
	}

}
