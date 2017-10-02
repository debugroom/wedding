package org.debugroom.wedding.domain.service.message;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.debugroom.wedding.domain.entity.message.Group;
import org.debugroom.wedding.domain.entity.message.GroupRelatedMessageBoard;
import org.debugroom.wedding.domain.entity.message.GroupRelatedUser;
import org.debugroom.wedding.domain.entity.message.Message;
import org.debugroom.wedding.domain.entity.message.MessageBoard;
import org.debugroom.wedding.domain.entity.message.MessageBoardRelatedGroup;
import org.debugroom.wedding.domain.entity.message.MessagePK;
import org.debugroom.wedding.domain.entity.message.User;
import org.debugroom.wedding.domain.entity.message.UserRelatedGroup;
import org.debugroom.wedding.domain.repository.cassandra.message.GroupRelatedMessageBoardRepository;
import org.debugroom.wedding.domain.repository.cassandra.message.GroupRelatedUserRepository;
import org.debugroom.wedding.domain.repository.cassandra.message.GroupRepository;
import org.debugroom.wedding.domain.repository.cassandra.message.MessageBoardRelatedGroupRepository;
import org.debugroom.wedding.domain.repository.cassandra.message.MessageBoardRepository;
import org.debugroom.wedding.domain.repository.cassandra.message.MessageRepository;
import org.debugroom.wedding.domain.repository.cassandra.message.UserRelatedGroupRepository;
import org.debugroom.wedding.domain.repository.cassandra.message.UserRepository;

@Service("messageService")
public class MessageServiceImpl implements MessageService{

	@Inject
	UserRepository userRepository;
	@Inject
	GroupRepository groupRepository;
	@Inject
	MessageBoardRepository messageBoardRepository;
	@Inject
	MessageRepository messageRepository;
	@Inject
	UserRelatedGroupRepository userRelatedGroupRepository;
	@Inject
	GroupRelatedMessageBoardRepository groupRelatedMessageBoardRepository;
	@Inject
	MessageBoardRelatedGroupRepository messageBoardRelatedGroupRepository;
	@Inject
	GroupRelatedUserRepository groupRelatedUserRepository;

	@Override
	public List<Message> getMessages(MessageBoard messageBoard) {
		List<Message> messages = messageRepository.findByMessagepkMessageBoardId(
				messageBoard.getMessageBoardId());
		Map<String, User> usersMap = userRepository.findAllForMap();
		for(Message message : messages){
			message.setUser(usersMap.get(message.getUserId()));
		}
		return messages;
	}

	@Override
	public List<MessageBoard> getMessageBoards(User user) {
		List<UserRelatedGroup> userRelatedGroups = 
				userRelatedGroupRepository.findByUserRelatedGrouppkUserId(
						user.getUserId());
		List<Long> groupIds = new ArrayList<Long>();
		for(UserRelatedGroup userRelatedGroup : userRelatedGroups){
			groupIds.add(userRelatedGroup.getUserRelatedGrouppk().getGroupId());
		}
		List<MessageBoard> messageBoards = new ArrayList<MessageBoard>();
		for(Group group : groupRepository.findByGroupIdIn(groupIds)){
			List<GroupRelatedMessageBoard> groupRelatedMessageBoards = 
					groupRelatedMessageBoardRepository
					.findByGroupRelatedMessageBoardpkGroupId(group.getGroupId());
			for(GroupRelatedMessageBoard groupRelatedMessageBoard : groupRelatedMessageBoards){
				messageBoards.add(messageBoardRepository.findOne(
						groupRelatedMessageBoard.getGroupRelatedMessageBoardpk()
						.getMessageBoardId()));
			}
		}
 		return messageBoards;
	}

	@Override
	public List<User> getUsers(MessageBoard messageBoard) {
		List<MessageBoardRelatedGroup> messageBoardRelatedGroups = 
				messageBoardRelatedGroupRepository.
				findByMessageBoardRelatedGrouppkMessageBoardId(messageBoard.getMessageBoardId());
		List<Long> groupIds = new ArrayList<Long>();
		for(MessageBoardRelatedGroup messageBoardRelatedGroup : messageBoardRelatedGroups){
			groupIds.add(messageBoardRelatedGroup.getMessageBoardRelatedGrouppk().getGroupId());
		}
		List<User> users = new ArrayList<User>();
		for(Group group : groupRepository.findByGroupIdIn(groupIds)){
			List<GroupRelatedUser> groupRelatedUsers =
					groupRelatedUserRepository.findByGroupRelatedUserpkGroupId(group.getGroupId());
			List<String> userIds = new ArrayList<String>();
 			for(GroupRelatedUser groupRelatedUser : groupRelatedUsers){
 				userIds.add(groupRelatedUser.getGroupRelatedUserpk().getUserId());
			}
 			users.addAll(userRepository.findByUserIdIn(userIds));
		}
		return users;
	}

	@Override
	public Message saveMessage(Message inputMessage) {
		Long messageBoardId = inputMessage.getMessagepk().getMessageBoardId();
		List<Message> messages = messageRepository
				.findByMessagepkMessageBoardId(messageBoardId);
		long maxMessageNo = 0;
		for(Message message : messages){
			if(maxMessageNo < message.getMessagepk().getMessageNo()){
				maxMessageNo = message.getMessagepk().getMessageNo();
			}
		}
		return messageRepository.save(Message.builder()
				.messagepk(MessagePK.builder()
									.messageBoardId(messageBoardId)
									.messageNo((long)maxMessageNo+1)
									.build())
				.comment(inputMessage.getComment())
				.userId(inputMessage.getUser().getUserId())
				.user(userRepository.findOne(inputMessage.getUser().getUserId()))
				.likeCount(0)
				.ver(0)
				.lastUpdatedDate(new Date())
				.build());
	}

	@Override
	public Message deleteMessage(Message message) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public MessageBoard saveMessageBoard(MessageBoard messageBoard, Group group) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public MessageBoard updateMessageBoard(MessageBoard messageBoard, Group group) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public MessageBoard deleteMessageBoard(MessageBoard messageBoard) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public Message updateMessage(Message message) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public User saveUser(User user) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public User updateUser(User user) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public User deleteUser(User user) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public Group saveGroup(Group group) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public Group updateGroup(Group group) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public Group deleteGroup(Group group) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public Group addUsersToGroup(Group group, List<User> users) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public Group deleteUsersToGroup(Group group, List<User> users) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

}
