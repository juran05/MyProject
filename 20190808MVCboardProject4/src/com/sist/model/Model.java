package com.sist.model;

import javax.servlet.http.HttpServletRequest;

public interface Model {
/*	
 	상속도
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
	public String handlerRequest(HttpServletRequest request) throws Throwable; //throws 예외처리 Exception보다 상위클래스,
	
}
