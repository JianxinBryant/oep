package com.zr.utils;

import java.io.IOException;
import java.util.ArrayList;

import javax.websocket.Session;

public class DanMuRoom {
	public static ArrayList<Session> room = new ArrayList<Session>();

	public static void sendMsgToOthers(String msg) {
		for (Session session : room) {
			try {
				if (session != null)
					session.getBasicRemote().sendText(msg);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
