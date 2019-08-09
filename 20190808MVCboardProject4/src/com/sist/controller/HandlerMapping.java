package com.sist.controller;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import java.util.*;
public class HandlerMapping extends DefaultHandler {
	Map map = new HashMap();
	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		// qname(태그명) , attributes(속성) 두개만 알고넘어가기
		// 어떤태그에 어떤 속성을 가져갈 것인지
		
		// 파싱하기~~~~ BCD!!!
		try {
			if(qName.equals("bean")) { //데이터를 bean 태그만 읽어라
				String id = attributes.getValue("id"); //id의 속성값을 읽어라
				String cls = attributes.getValue("class"); //class의 속성값을 읽어라
				
				
				//▼ 이부분이 application-context클래스 들을 생성한ㄷ다!!
				Class clsName = Class.forName(cls); //메모리할당
				Object obj = clsName.newInstance(); //newInstance의 리턴형이 Object기 때문에 맞춰주는 것이다.
				map.put(id,obj); //값을 map에 저장
			}
		} catch (Exception ex) { }
	}
	
}
