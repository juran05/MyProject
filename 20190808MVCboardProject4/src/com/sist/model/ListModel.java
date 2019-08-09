package com.sist.model;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import com.sist.dao.*;

public class ListModel implements Model {

	@Override
	public String handlerRequest(HttpServletRequest request) throws Throwable {
		//request.setAttribute("msg", "게시판 목록"); 오전
		
		//목록받기
		//페이지받기
		String page = request.getParameter("page");
		if(page==null) { // 시작하자마자 null이라서 page를 1로 지정해주고 시작한다
			page="1";
		} 
		int curpage = Integer.parseInt(page);
		int rowSize = 10;
		int start = (curpage*rowSize)-(rowSize-1);
		int end = curpage*rowSize;
		
		//map에 저장
		Map map = new HashMap();
		map.put("start", start); // 키값이 board-mapper.xml 의 #{start} AND #{end} 값과 일치해야함
		map.put("end", end);
		
		List<BoardVO> list = BoardDAO.boardListData(map);
		//list.jsp 전송
		request.setAttribute("list", list);
		
		return "boardview/list.jsp";
	}

}
