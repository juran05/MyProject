package com.sist.model;

import javax.servlet.http.HttpServletRequest;
import com.sist.dao.*;

public class UpdateModel implements Model {

	@Override
	public String handlerRequest(HttpServletRequest request) throws Throwable {
		// 데이터받기 (게시물번호)
		String no = request.getParameter("no");
		BoardVO vo = BoardDAO.boardUpdateData(Integer.parseInt(no));
		
		
		//데이터보내기] 전송할 값실기
		request.setAttribute("vo", vo);
		
		//데이터보내기 ] update.jsp에 리퀘스트 추가된 값 vo을 보내기, 즉 리턴형 밑에 싣는값이 들어갑니다~
		return "boardview/update.jsp";
	}

}
