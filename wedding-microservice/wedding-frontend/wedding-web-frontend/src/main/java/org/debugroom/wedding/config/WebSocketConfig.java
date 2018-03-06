package org.debugroom.wedding.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig extends AbstractWebSocketMessageBrokerConfigurer{

	@Value("${server.contextPath}")
	private String contextPath;
	
	
	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		registry.addEndpoint("messages/broadcast", 
				"messages/{messageBoardId}",
				 "notifications/{folderId}")
		.withSockJS();
	}

	@Override
	public void configureMessageBroker(MessageBrokerRegistry registry) {
		registry.setApplicationDestinationPrefixes(
				new StringBuilder().append(contextPath).append("/chat").toString(),
				new StringBuilder().append(contextPath).append("/gallery").toString()
				);
		registry.enableSimpleBroker("/topic", "/queue", "/notification");
	}

}
