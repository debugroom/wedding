package org.debugroom.wedding.app.web.message;

import java.util.List;

import javax.inject.Inject;

import org.dozer.Mapper;

import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.debugroom.framework.common.exception.BusinessException;
import org.debugroom.wedding.app.model.message.ChatPortalResource;
import org.debugroom.wedding.app.model.message.UpdateMessageBoardForm;
import org.debugroom.wedding.domain.entity.message.Group;
import org.debugroom.wedding.domain.entity.message.Message;
import org.debugroom.wedding.domain.entity.message.MessageBoard;
import org.debugroom.wedding.domain.entity.message.User;
import org.debugroom.wedding.domain.service.message.MessageService;


@RestController
@RequestMapping("/api/v1")
public class MessageRestController {

	@Inject
	MessageService messageService;
	
	@RequestMapping(method=RequestMethod.GET, value="/portal/{userId}")
	public ChatPortalResource getChatPortalResource(@PathVariable String userId){
		return ChatPortalResource.builder().messageBoards(
				messageService.getMessageBoards(User.builder().userId(userId).build()))
				.build();
	}

	@RequestMapping(method=RequestMethod.GET, value="/messages/{messageBoardId}")
	public List<Message> getMessages(@PathVariable Long messageBoardId){
		return messageService.getMessages(MessageBoard.builder()
				.messageBoardId(messageBoardId).build());
	}

	@RequestMapping(method=RequestMethod.POST, value="/message/new")
	public Message saveMessage(@RequestBody Message message){
		return messageService.saveMessage(message);
	}

	@RequestMapping(method=RequestMethod.GET, value="/{messageBoardId}/users")
	public List<User> getUsersByMessageBoard(@PathVariable Long messageBoardId){
		return messageService.getUsers(MessageBoard.builder()
				.messageBoardId(messageBoardId).build());
	}

	@RequestMapping(method=RequestMethod.GET, value="/users")
	public List<User> getUsers(){
		return messageService.getUsers();
	}

	@RequestMapping(method=RequestMethod.POST, value="/message-board/new")
	public MessageBoard saveMessageBoard(@RequestBody Group group){
		return messageService.saveMessageBoard(group);
	}

	@RequestMapping(method=RequestMethod.GET, value="/message-board/{messageBoardId}")
	public MessageBoard getMessageBoard(@PathVariable Long messageBoardId) 
			throws BusinessException{
		return messageService.getMessageBoard(
				MessageBoard.builder().messageBoardId(messageBoardId).build());
	}

	@RequestMapping(method=RequestMethod.GET, value="/message-board/{messageBoardId}/not-users")
	public List<User> getNotUsers(@PathVariable Long messageBoardId){
		return messageService.getNotUsers(
				MessageBoard.builder().messageBoardId(messageBoardId).build());
	}

	@RequestMapping(method=RequestMethod.PUT, value="/message-board/{messageBoardId}")
	public MessageBoard updateMessageBoard(
			@RequestBody UpdateMessageBoardForm updateMessageBoardForm){
		return messageService.updateMessageBoard(
				updateMessageBoardForm.getMessageBoard(), 
				updateMessageBoardForm.getCheckedAddUsers(), 
				updateMessageBoardForm.getCheckedDeleteUsers());
	}

	@RequestMapping(method=RequestMethod.DELETE, value="/message-board/{messageBoardId}")
	public MessageBoard deleteMessageBoard(@PathVariable Long messageBoardId){
		return messageService.deleteMessageBoard(MessageBoard.builder()
				.messageBoardId(messageBoardId).build());
	}

}
