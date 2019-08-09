package com.sist.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.sist.model.*;

public class DispatcherServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private WebApplicationContext app;
	
	public void init(ServletConfig config) throws ServletException {
		// web.xml -> xml파일 경로 읽기, 즉 파싱하기 파싱A
		String path = config.getInitParameter("xmlPath");
		app = new WebApplicationContext(path); 
	}

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String cmd = request.getRequestURI();
			cmd = cmd.substring(request.getContextPath().length()+1);
			
			Model model = (Model)app.getBean(cmd); //[클래스찾기] 어제랑 다른 부분은 이 한줄, 찾는 부분만 클래스로 빼놨다!!!
			String jsp = model.handlerRequest(request);
			
			if(jsp.startsWith("redirect")) {
				response.sendRedirect(jsp.substring(jsp.indexOf(":")+1));
			} else {
				RequestDispatcher rd = request.getRequestDispatcher(jsp);
				rd.forward(request, response);
			}
			
		} catch (Throwable ex) { // 위에 String jsp = model.handlerRequest(request);가 Exception보다 상위 객체를 갖고있어서 더 위인 Throwable 이걸쓴다
			ex.printStackTrace();// 궁금하면요 ListModel, InsertModel... 여기가보세요 뭘 상속하고있는지
		}
		
	}

}
