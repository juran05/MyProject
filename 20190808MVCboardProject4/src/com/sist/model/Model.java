package com.sist.model;

import javax.servlet.http.HttpServletRequest;

public interface Model {
/*	
 	��ӵ�
	Object
		|
	Throwable
		|
	Error, Exception
				|
			CheckException	UnCheckException
			============	============== RuntimeException
NumberFormatException	=	IOException
NullPointerException			=	SQLException
ArrayIndexOutofBoundsException	=	ClassNotFoundException
ClassCastException			=	MalformURLException
*/
	public String handlerRequest(HttpServletRequest request) throws Throwable; //throws ����ó�� Exception���� ����Ŭ����,
	
}
