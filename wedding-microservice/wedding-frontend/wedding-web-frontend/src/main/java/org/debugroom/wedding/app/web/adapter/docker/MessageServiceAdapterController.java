package org.debugroom.wedding.app.web.adapter.docker;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import org.debugroom.framework.common.exception.BusinessException;
import org.debugroom.wedding.app.model.UserSearchResult;
import org.debugroom.wedding.app.model.message.AddMessageBoardResult;
import org.debugroom.wedding.app.model.message.ChatPortalResource;
import org.debugroom.wedding.app.model.message.DeleteMessageBoardResult;
import org.debugroom.wedding.app.model.message.GetMessagesResult;
import org.debugroom.wedding.app.model.message.Group;
import org.debugroom.wedding.app.model.message.Message;
import org.debugroom.wedding.app.model.message.MessageBoard;
import org.debugroom.wedding.app.model.message.UpdateMessageBoardForm;
import org.debugroom.wedding.app.model.message.UpdateMessageBoardResource;
import org.debugroom.wedding.app.model.message.UpdateMessageBoardResult;
import org.debugroom.wedding.app.web.adapter.docker.provider.ConnectPathProvider;
import org.debugroom.wedding.app.web.helper.ImageDownloadHelper;
import org.debugroom.wedding.app.web.security.CustomUserDetails;
import org.debugroom.wedding.app.web.util.RequestBuilder;
import org.debugroom.wedding.domain.entity.User;

@Controller
public class MessageServiceAdapterController {

	private static final String APP_NAME = "api/v1";

	@Value("${server.contextPath}")
	private String contextPath;

	@Inject
	Mapper mapper;
	
	@Inject
	ConnectPathProvider provider;
	
	@Inject
	ImageDownloadHelper downloadHelper;
	
	@RequestMapping(method=RequestMethod.GET, value="/chat/portal/{userId:[0-9]+}")
	public String chat(@PathVariable String userId, Model model,
			@AuthenticationPrincipal CustomUserDetails customUserDetails){
		if(!userId.equals(customUserDetails.getUser().getUserId())){
			model.addAttribute("errorCode", "9000");
			return "common/error";
		}
		String serviceName = "message";
		RestTemplate restTemplate = new RestTemplate();
		model.addAttribute(restTemplate.getForObject(
				RequestBuilder.buildUriComponents(serviceName, 
						new StringBuilder()
						.append(APP_NAME)
						.append("/portal/{userId}")
						.toString(), provider)
				.expand(userId).toUri(), ChatPortalResource.class));
		return "chat/portal";
	}

	@RequestMapping(method = RequestMethod.GET,
			headers = "Accept=image/jpeg, image/jpg, image/png, image/gif",
			produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_GIF_VALUE},
			value = "/chat/profile/image/{userId:[0-9]+}/{imageFileExtension}")
	@ResponseBody
	public ResponseEntity<BufferedImage> getChatProfileImage(@PathVariable String userId,
			@AuthenticationPrincipal CustomUserDetails customUserDetails){
		String serviceName = "message";
		RestTemplate restTemplate = new RestTemplate();
		User user = restTemplate.getForObject(
				RequestBuilder.buildUriComponents(serviceName, 
						new StringBuilder()
						.append(APP_NAME)
						.append("/profile/{userId}")
						.toString(), provider).expand(userId).toUri(), User.class);
		BufferedImage image = null;
		try{
			image = downloadHelper.getProfileImage(user);
		}catch(BusinessException e){
			return ResponseEntity.badRequest().body(null);
		}
		return ResponseEntity.ok().body(image);
	}

	@RequestMapping(method=RequestMethod.GET, value="/chat/message-board/{messageBoardId}")
	public ResponseEntity<GetMessagesResult> getMessages(
			@Validated MessageBoard messageBoard, BindingResult bindingResult, Model model){

		GetMessagesResult getMessagesResult = GetMessagesResult.builder().build();

		if(bindingResult.hasErrors()){
			List<String> errorMessages = new ArrayList<String>();
			getMessagesResult.setErrorMessages(errorMessages);
			for(FieldError fieldError : bindingResult.getFieldErrors()){
				errorMessages.add(fieldError.getDefaultMessage());
			}
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(getMessagesResult);
		}
		String serviceName = "message";
		RestTemplate restTemplate = new RestTemplate();
		getMessagesResult.setMessages(Arrays.asList(restTemplate.getForObject(
				RequestBuilder.buildUriComponents(serviceName, 
						new StringBuilder()
						.append(APP_NAME)
						.append("/messages/{messageBoardId}")
						.toString(), provider)
				.expand(messageBoard.getMessageBoardId()).toUri(), Message[].class)));
		getMessagesResult.setRequestContextPath(contextPath);
		return ResponseEntity.status(HttpStatus.OK).body(getMessagesResult);

	}
	
	@RequestMapping(method=RequestMethod.GET, value="/chat/users")
	public ResponseEntity<UserSearchResult> chatUsers(){
		String serviceName = "message";
		RestTemplate restTemplate = new RestTemplate();
		User[] users = restTemplate.getForObject(
				RequestBuilder.buildUriComponents(serviceName, 
						new StringBuilder()
						.append(APP_NAME)
						.append("/users")
						.toString(), provider).toUri(), User[].class);
		return ResponseEntity.status(HttpStatus.OK)
				.body(UserSearchResult.builder().users(
						Arrays.asList(users)).build());
	}
	
	@RequestMapping(method=RequestMethod.POST, value="/chat/group/new")
	public ResponseEntity<AddMessageBoardResult> addGroup(
			@AuthenticationPrincipal CustomUserDetails customUserDetails,
			@RequestBody @Validated Group group, BindingResult bindingResult){
		
		AddMessageBoardResult addMessageBoardResult = AddMessageBoardResult
				.builder().userId(customUserDetails.getUser().getUserId()).build();

		if(bindingResult.hasErrors()){
			List<String> messages = new ArrayList<String>();
			addMessageBoardResult.setMessages(messages);
			for(FieldError fieldError : bindingResult.getFieldErrors()){
				messages.add(fieldError.getDefaultMessage());
			}
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(addMessageBoardResult);
		}
		
		String serviceName = "message";
		RestTemplate restTemplate = new RestTemplate();
		addMessageBoardResult.setMessageBoard(restTemplate.postForObject(
				RequestBuilder.buildUriComponents(serviceName, 
						new StringBuilder()
						.append(APP_NAME)
						.append("/message-board/new")
						.toString(), provider).toUri(), group, MessageBoard.class));
		addMessageBoardResult.setRequestContextPath(contextPath);
		return ResponseEntity.status(HttpStatus.OK).body(addMessageBoardResult);
	}

	@RequestMapping(method=RequestMethod.GET, 
			value="/chat/update/message-board/{messageBoardId:[0-9]+}")
	public ResponseEntity<UpdateMessageBoardResource> getUpdateMessageBoardResource(
			@PathVariable String messageBoardId){
		
		UpdateMessageBoardResource updateMessageBoardResource =
			UpdateMessageBoardResource.builder().build();
		
		String serviceName = "message";
		RestTemplate restTemplate = new RestTemplate();
		updateMessageBoardResource.setMessageBoard(
				restTemplate.getForObject(
						RequestBuilder.buildUriComponents(serviceName, 
								new StringBuilder()
								.append(APP_NAME)
								.append("/message-board/{messageBoardId}")
								.toString(), provider)
						.expand(messageBoardId).toUri(), MessageBoard.class));
		updateMessageBoardResource.setNotBelongUsers(Arrays.asList(
				restTemplate.getForObject(
						RequestBuilder.buildUriComponents(serviceName, 
								new StringBuilder()
								.append(APP_NAME)
								.append("/message-board/{messageBoardId}/not-users")
								.toString(), provider)
						.expand(messageBoardId).toUri(),
						org.debugroom.wedding.app.model.message.User[].class)));
		updateMessageBoardResource.setRequestContextPath(contextPath);
		return ResponseEntity.status(HttpStatus.OK).body(updateMessageBoardResource);
	}

	@RequestMapping(method=RequestMethod.POST, value="/chat/update/message-board/{messageBoardId:[0-9]+}")
	public ResponseEntity<UpdateMessageBoardResult> updateMessageBoard(
			@PathVariable String messageBoardId, 
			@AuthenticationPrincipal CustomUserDetails customUserDetails,
			@RequestBody @Validated UpdateMessageBoardForm updateMessageBoardForm,
			BindingResult bindingResult){
		
		UpdateMessageBoardResult updateMessageBoardResult = UpdateMessageBoardResult
				.builder().userId(customUserDetails.getUser().getUserId()).build();

		if(bindingResult.hasErrors()){
			List<String> messages = new ArrayList<String>();
			updateMessageBoardResult.setMessages(messages);
			for(FieldError fieldError : bindingResult.getFieldErrors()){
				messages.add(fieldError.getDefaultMessage());
			}
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(updateMessageBoardResult);
		}
		
		String serviceName = "message";
		RestTemplate restTemplate = new RestTemplate();
		updateMessageBoardResult.setMessageBoard(restTemplate.exchange(
				RequestBuilder.buildUriComponents(serviceName, 
						new StringBuilder()
						.append(APP_NAME)
						.append("/message-board/{messageBoardId}")
						.toString(), provider)
				.expand(messageBoardId).toUri(), HttpMethod.PUT, 
				new HttpEntity<UpdateMessageBoardForm>(updateMessageBoardForm), 
				MessageBoard.class).getBody());
		updateMessageBoardResult.setRequestContextPath(contextPath);
		return ResponseEntity.status(HttpStatus.OK).body(updateMessageBoardResult);
	}

	@RequestMapping(method=RequestMethod.DELETE, 
			value="/chat/delete/message-board/{messageBoardId:[0-9]+}")
	public ResponseEntity<DeleteMessageBoardResult> deleteMessageBoard(
			@PathVariable String messageBoardId){
		
		DeleteMessageBoardResult deleteMessageBoardResult = DeleteMessageBoardResult
				.builder().build();
	
		String serviceName = "message";
		RestTemplate restTemplate = new RestTemplate();
		deleteMessageBoardResult.setMessageBoard(
				restTemplate.exchange(
						RequestBuilder.buildUriComponents(serviceName, 
								new StringBuilder()
								.append(APP_NAME)
								.append("/message-board/{messageBoardId}")
								.toString(), provider)
						.expand(messageBoardId).toUri(), HttpMethod.DELETE, 
						null, MessageBoard.class).getBody());
		return ResponseEntity.status(HttpStatus.OK).body(deleteMessageBoardResult);
	}
	
}
