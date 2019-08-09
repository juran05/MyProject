package com.sist.controller;
import java.util.*;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.helpers.DefaultHandler;

import java.io.*;

public class WebApplicationContext {
	private Map map = new HashMap();
	//XML�� handelr mapping�� �����ְ� ����
	public WebApplicationContext(String path) { // Model Ŭ������ ����, application-context.xml(Model���դ�����)
		try {
			SAXParserFactory spf = SAXParserFactory.newInstance(); //�ļ��⸦ ���������ִ� ��Ȱ
			SAXParser sp = spf.newSAXParser(); //�ļ���
									//-----�ļ��������,�ļ��� �� ���θ����? 
									// ML �� HTML, XML(����), WML(***����Ͽ� XML), HDML �̷��� ��� ������ �𸣴ϱ�
									// application-context.xml �� 4���� Ŭ������ ��Ҵٰ� �ѷ��ֱ⶧����!!!!
									// Ŭ���� �������� ��Ƴ��ٰ� �ѹ��� �ѷ��ִ°� Factory�����̶� �Ѵ� = ���߿� �������� ���丮���Ͼ�
									
									// �̱�������, ���丮���� ���ݱ��� ��ǥ���ΰ� �ΰ� �����~!!!
			//XML �Ľ��Ѱ�, public class HandlerMapping extends DefaultHandler ����Ʈ�ڵ鷯 : XML�� ���پ� ����
			//DefaultHandler ��ü�� ������� �Ȱ����־ 
			//���ο� �޼ҵ����(�������̵�) �ؼ� ����� �߰����ش�
			//�׷��� ����ö��� HandlerMapping �Ÿ� �����´�
			//�١١ٻ轺�Ľ��� �׻� ����� �ް� �������̵� �ؾ��Ѵ�
			
			HandlerMapping hm = new HandlerMapping();
			sp.parse(new File(path), hm); // �ڵ鷯������ �����ִ� ��ŸƮ������Ʈ�� �����´�.
			
			map = hm.map;
			
		} catch (Exception ex) { }
	}
	public Object getBean(String id) { // Class �ٽø��� Model�� ã���ش�
		return map.get(id);
	}
}
