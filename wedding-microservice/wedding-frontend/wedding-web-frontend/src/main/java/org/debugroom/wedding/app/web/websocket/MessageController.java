package org.debugroom.wedding.app.web.websocket;

import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.inject.Inject;

import org.debugroom.wedding.app.model.message.Message;
import org.debugroom.wedding.app.model.message.User;
import org.debugroom.wedding.app.web.adapter.docker.provider.ConnectPathProvider;

@Controller
public class MessageController {

	private static final String PROTOCOL = "http";
	private static final String APP_NAME = "api/v1";

	@Inject
	ConnectPathProvider provider;
	
	@Inject
	SimpMessagingTemplate simpMessagingTemplate;
	
	@MessageMapping("/messages/broadcast")
	@SendTo("/topic/broadcast")
	public Message broadCast(Message message){
		return postMessage(message);
	}

	@MessageMapping("/messages/{messageBoardId}")
	public void pushToSpecifiedUsers(@DestinationVariable Long messageBoardId, Message message){
		Message addMessage = postMessage(message);
		String serviceName = "message";
		RestTemplate restTemplate = new RestTemplate();
		UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.newInstance();
		UriComponents uriComponents = uriComponentsBuilder.scheme(PROTOCOL)
										.host(provider.getIpAddr(serviceName))
										.port(provider.getPort(serviceName))
										.path(new StringBuilder()
												.append(APP_NAME)
												.append("/")
												.append(Long.toString(messageBoardId))
												.append("/users")
												.toString())
										.build();
		User[] users = restTemplate.getForObject(uriComponents.toUri(), User[].class);
		for(User user : users){
			simpMessagingTemplate.convertAndSend(
				new StringBuilder()
				.append("/topic/user-").append(user.getUserId()).toString(),
						addMessage);
		}
	}

	private Message postMessage(Message message){
		String serviceName = "message";
		RestTemplate restTemplate = new RestTemplate();
		UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.newInstance();
		UriComponents uriComponents = uriComponentsBuilder.scheme(PROTOCOL)
										.host(provider.getIpAddr(serviceName))
										.port(provider.getPort(serviceName))
										.path(new StringBuilder()
												.append(APP_NAME)
												.append("/message/new")
												.toString())
										.build();
		return restTemplate.postForObject(
				uriComponents.toUri(), message, Message.class);
		
	}

}
