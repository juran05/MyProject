package com.sist.controller;
import java.util.*;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.helpers.DefaultHandler;

import java.io.*;

public class WebApplicationContext {
	private Map map = new HashMap();
	//XML을 handelr mapping에 보내주고 관리
	public WebApplicationContext(String path) { // Model 클래스를 관리, application-context.xml(Model집합ㅋㅋㅋ)
		try {
			SAXParserFactory spf = SAXParserFactory.newInstance(); //파서기를 생성시켜주는 역활
			SAXParser sp = spf.newSAXParser(); //파서기
									//-----파서기생성기,파서기 왜 따로만들어? 
									// ML → HTML, XML(웹용), WML(***모바일용 XML), HDML 이렇게 어떻게 들어올지 모르니까
									// application-context.xml 의 4가지 클래스를 모았다가 뿌려주기때문에!!!!
									// 클래스 여러개를 모아놨다가 한번에 뿌려주는걸 Factory패턴이라 한다 = 나중에 스프링이 팩토리패턴씀
									
									// 싱글턴패턴, 팩토리패턴 지금까지 대표적인거 두개 배웠따~!!!
			//XML 파싱한것, public class HandlerMapping extends DefaultHandler 디폴트핸들러 : XML을 한줄씩 읽음
			//DefaultHandler 자체는 별기능을 안갖고있어서 
			//새로운 메소드생성(오버라이딩) 해서 기능을 추가해준다
			//그래서 갖고올때는 HandlerMapping 거를 가져온다
			//☆☆☆삭스파싱은 항상 상속을 받고 오버라이딩 해야한다
			
			HandlerMapping hm = new HandlerMapping();
			sp.parse(new File(path), hm); // 핸들러매핑이 갖고있는 스타트엘리먼트를 가져온다.
			
			map = hm.map;
			
		} catch (Exception ex) { }
	}
	public Object getBean(String id) { // Class 다시말해 Model을 찾아준다
		return map.get(id);
	}
}
