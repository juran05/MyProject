package com.sist.controller;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import java.util.*;
public class HandlerMapping extends DefaultHandler {
	Map map = new HashMap();
	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		// qname(�±׸�) , attributes(�Ӽ�) �ΰ��� �˰�Ѿ��
		// ��±׿� � �Ӽ��� ������ ������
		
		// �Ľ��ϱ�~~~~ BCD!!!
		try {
			if(qName.equals("bean")) { //�����͸� bean �±׸� �о��
				String id = attributes.getValue("id"); //id�� �Ӽ����� �о��
				String cls = attributes.getValue("class"); //class�� �Ӽ����� �о��
				
				
				//�� �̺κ��� application-contextŬ���� ���� �����Ѥ���!!
				Class clsName = Class.forName(cls); //�޸��Ҵ�
				Object obj = clsName.newInstance(); //newInstance�� �������� Object�� ������ �����ִ� ���̴�.
				map.put(id,obj); //���� map�� ����
			}
		} catch (Exception ex) { }
	}
	
}
