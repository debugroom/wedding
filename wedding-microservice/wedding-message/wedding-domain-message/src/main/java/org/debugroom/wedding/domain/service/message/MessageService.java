package org.debugroom.wedding.domain.service.message;

import java.util.List;

import org.debugroom.framework.common.exception.BusinessException;
import org.debugroom.wedding.domain.entity.message.Group;
import org.debugroom.wedding.domain.entity.message.Message;
import org.debugroom.wedding.domain.entity.message.MessageBoard;
import org.debugroom.wedding.domain.entity.message.User;

public interface MessageService {
	
	public List<Message> getMessages(MessageBoard messageBoard);
	public List<MessageBoard> getMessageBoards(User user);
	public List<User> getUsers();
	public List<User> getUsers(MessageBoard messageBoard);
	public List<User> getNotUsers(MessageBoard messageBoard);
	public MessageBoard getMessageBoard(MessageBoard messageBoard) throws BusinessException;
	public MessageBoard saveMessageBoard(Group group);
	public MessageBoard saveMessageBoard(MessageBoard messageBoard, Group group);
	public MessageBoard updateMessageBoard(MessageBoard messageBoard, List<User> addusers, List<User> deleteUsers);
	public MessageBoard deleteMessageBoard(MessageBoard messageBoard);
	public Message saveMessage(Message message);
	public Message updateMessage(Message message);
	public Message deleteMessage(Message message);
	public void deleteMessages(MessageBoard messageBoard);
	public User getUser(String userId);
	public User saveUser(User user);
	public User updateUser(User user);
	public User deleteUser(User user);
	public Group saveGroup(Group group);
	public Group updateGroup(Group group);
	public Group deleteGroup(Group group);
	public Group addUsersToGroup(Group group, List<User> users);
	public Group deleteUsersToGroup(Group group, List<User> users);
	
}
