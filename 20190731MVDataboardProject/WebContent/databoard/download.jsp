<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="com.sist.model.*, java.io.*,java.net.*"%>
<jsp:useBean id="model" class="com.sist.model.Model"/>
<%
	//모델 안에서 곧장처리한다. Model.java로 가서 해결부터 하고오기
	//model.download(request, response);
	//list.jsp로 가기
	
	try {
			request.setCharacterEncoding("UTF-8");
			String fn=request.getParameter("fn");
			File file = new File("c:\\upload\\"+fn); // 경로를 이렇게 줘야 다운을 받을 수 있음
			
			System.out.println("file===========>"+file);
			
			// 다운로드창을 보여줌... 미리 보내줘야함 → download.jsp로 가기 
			response.setContentLength((int)file.length()); 
			response.setHeader("Content-Disposition", "attachment;filename="
												+URLEncoder.encode(fn, "UTF-8"));
			
			
			//Header 전송 → 다운로드창
			BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
		
			//클라이언트
			BufferedOutputStream bos = new BufferedOutputStream(response.getOutputStream()); // 요청한 사람에게 응답 
		
			byte[] buffer = new byte[1024];
			int i = 0;
			while((i=bis.read(buffer, 0, 1024))!= -1) {
				bos.write(buffer, 0, i);
			}
			out.clear();
			out=pageContext.pushBody();
			bis.close();
			bos.close();
			
		} catch (Exception ex) {

		}
%>