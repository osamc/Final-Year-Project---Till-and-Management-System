package com.sam.tillsystem.service;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.stereotype.Repository;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Repository
public class WebsocketService extends TextWebSocketHandler {

	List<WebSocketSession> sessions = new CopyOnWriteArrayList<WebSocketSession>();
	
	public void update() throws IOException {
	   
	    for(WebSocketSession s: sessions) {
	    	try {
		    	s.sendMessage(new TextMessage("update"));
	    	} catch (IllegalStateException | IOException e) {
	    		s.close();
	    	}
	    }
	}
	
	
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		sessions.remove(sessions.indexOf(session));
		super.afterConnectionClosed(session, status);
	}



	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		sessions.add(session);
	}

	
	

}