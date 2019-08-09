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
		// web.xml -> xml���� ��� �б�, �� �Ľ��ϱ� �Ľ�A
		String path = config.getInitParameter("xmlPath");
		app = new WebApplicationContext(path); 
	}

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String cmd = request.getRequestURI();
			cmd = cmd.substring(request.getContextPath().length()+1);
			
			Model model = (Model)app.getBean(cmd); //[Ŭ����ã��] ������ �ٸ� �κ��� �� ����, ã�� �κи� Ŭ������ ������!!!
			String jsp = model.handlerRequest(request);
			
			if(jsp.startsWith("redirect")) {
				response.sendRedirect(jsp.substring(jsp.indexOf(":")+1));
			} else {
				RequestDispatcher rd = request.getRequestDispatcher(jsp);
				rd.forward(request, response);
			}
			
		} catch (Throwable ex) { // ���� String jsp = model.handlerRequest(request);�� Exception���� ���� ��ü�� �����־ �� ���� Throwable �̰ɾ���
			ex.printStackTrace();// �ñ��ϸ�� ListModel, InsertModel... ���Ⱑ������ �� ����ϰ��ִ���
		}
		
	}

}
