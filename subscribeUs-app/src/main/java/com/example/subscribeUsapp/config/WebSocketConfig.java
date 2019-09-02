package com.example.subscribeUsapp.config;

import com.example.subscribeUsapp.handler.MyWebSocketHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.reactive.HandlerMapping;
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping;
import org.springframework.web.reactive.socket.server.WebSocketService;
import org.springframework.web.reactive.socket.server.support.HandshakeWebSocketService;
import org.springframework.web.reactive.socket.server.support.WebSocketHandlerAdapter;
import org.springframework.web.reactive.socket.server.upgrade.ReactorNettyRequestUpgradeStrategy;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class WebSocketConfig {

	@Autowired
	private MyWebSocketHandler myWebSocketHandler;

	@Bean
	public HandlerMapping handlerMapping() {
		Map<String, MyWebSocketHandler> map = new HashMap<>();
		map.put("/subscribe", myWebSocketHandler);

		SimpleUrlHandlerMapping mapping = new SimpleUrlHandlerMapping();
		mapping.setUrlMap(map);
		mapping.setOrder(Ordered.HIGHEST_PRECEDENCE);
		return mapping;
	}

	@Bean
	public WebSocketHandlerAdapter handlerAdapter(WebSocketService webSocketService) {
		return new WebSocketHandlerAdapter(webSocketService);
	}

	@Bean
	public WebSocketService webSocketService() {
		ReactorNettyRequestUpgradeStrategy strategy = new ReactorNettyRequestUpgradeStrategy();
		return new HandshakeWebSocketService(strategy);
	}

}
