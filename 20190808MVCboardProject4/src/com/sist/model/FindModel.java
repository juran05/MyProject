package com.sist.model;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import com.sist.dao.*;

public class FindModel implements Model {

	@Override
	public String handlerRequest(HttpServletRequest request) throws Throwable {
			
		try {
			request.setCharacterEncoding("UTF-8");
		} catch (Exception ex) { } // 에러 날 이유가 없어서 printstack어쩌구 안씀 
		
			// DB연동 데이터받기
				String fs = request.getParameter("fs");
				String ss = request.getParameter("ss");
	
			// DB로 값보냈다가
			// DAO가 받는게 (BoardVO에서) 맵으로 받기 때문에 = VO에 값없음
			// Map하나 만들고가기
				Map map = new HashMap();
				map.put("fs", fs); // WHERE ${fs} LIKE '%'||#{ss}||'%'
				map.put("ss", ss); // (키,값)
				
				List<BoardVO> list = BoardDAO.boardFindData(map);
				// 묶어서 request.setAttribute 값보내기
				request.setAttribute("list", list);
				request.setAttribute("count", list.size()); //검색 갯수0일때 테이블만 덩하니 안보이게 
		return "boardview/find.jsp";
	}
}
