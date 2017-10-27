package com.zr.listener;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;

import com.zr.pojo.UserInfo;

public class MyListener implements HttpSessionAttributeListener {

	Map<String, HttpSession> map = new HashMap<String, HttpSession>();

	@Override
	public void attributeAdded(HttpSessionBindingEvent event) {
		String name = event.getName();
		System.out.println("监听到增加属性"+"name="+name);
		if (name.equals("userInfo")) {
			
			UserInfo userInfo = (UserInfo) event.getValue();
//			map.put(userInfo.getAddress(), event.getSession());
			System.out.println("zzz" + map.get(userInfo.getAddress()));
//			if (map.get(userInfo.getAddress()) != null) {
//				System.out.println("2222");
//				HttpSession session = map.get(userInfo.getAddress());
//				UserInfo userInfo1 = (UserInfo) session.getAttribute("userInfo");
//				session.removeAttribute("userInfo");
//				session.invalidate();
//			}
			Iterator<Map.Entry<String, HttpSession>> it = map.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry<String, HttpSession> entry = it.next();
				System.out.println("key= " + entry.getKey() + " and value= " + entry.getValue());
				if(userInfo.getUsername().equals(entry.getValue().getAttribute("username"))){
					entry.getValue().setAttribute("username", userInfo.getUsername());
					break;
				}
			}
			System.out.println();
			map.put(userInfo.getAddress(), event.getSession());
		}
		
	}

	@Override
	public void attributeRemoved(HttpSessionBindingEvent event) {
		String name = event.getName();
		System.out.println("监听到移除属性");
		if (name.equals("userInfo")) {
			UserInfo userInfo = (UserInfo) event.getValue();
			map.remove(userInfo.getAddress());
		}
		Iterator<Map.Entry<String, HttpSession>> it = map.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<String, HttpSession> entry = it.next();
			System.out.println("key= " + entry.getKey() + " and value= " + entry.getValue());
		}
	}

	@Override
	public void attributeReplaced(HttpSessionBindingEvent event) {
		// TODO Auto-generated method stub

	}

}
