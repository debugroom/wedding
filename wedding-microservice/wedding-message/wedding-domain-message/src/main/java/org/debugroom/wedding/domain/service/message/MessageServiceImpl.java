package org.debugroom.wedding.domain.service.message;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.debugroom.framework.common.exception.BusinessException;
import org.debugroom.wedding.domain.entity.message.Group;
import org.debugroom.wedding.domain.entity.message.GroupRelatedMessageBoard;
import org.debugroom.wedding.domain.entity.message.GroupRelatedMessageBoardPK;
import org.debugroom.wedding.domain.entity.message.GroupRelatedUser;
import org.debugroom.wedding.domain.entity.message.GroupRelatedUserPK;
import org.debugroom.wedding.domain.entity.message.Message;
import org.debugroom.wedding.domain.entity.message.MessageBoard;
import org.debugroom.wedding.domain.entity.message.MessageBoardRelatedGroup;
import org.debugroom.wedding.domain.entity.message.MessageBoardRelatedGroupPK;
import org.debugroom.wedding.domain.entity.message.MessagePK;
import org.debugroom.wedding.domain.entity.message.User;
import org.debugroom.wedding.domain.entity.message.UserRelatedGroup;
import org.debugroom.wedding.domain.entity.message.UserRelatedGroupPK;
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
	public List<User> getUsers(){
		return (List<User>)userRepository.findAll();
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
	public List<User> getNotUsers(MessageBoard messageBoard){
		List<MessageBoardRelatedGroup> messageBoardRelatedGroups = 
				messageBoardRelatedGroupRepository.
				findByMessageBoardRelatedGrouppkMessageBoardId(messageBoard.getMessageBoardId());
		List<Long> groupIds = new ArrayList<Long>();
		for(MessageBoardRelatedGroup messageBoardRelatedGroup : messageBoardRelatedGroups){
			groupIds.add(messageBoardRelatedGroup.getMessageBoardRelatedGrouppk().getGroupId());
		}
		Map<String, User> userMap = userRepository.findAllForMap();
		for(Group group : groupRepository.findByGroupIdIn(groupIds)){
			List<GroupRelatedUser> groupRelatedUsers =
					groupRelatedUserRepository.findByGroupRelatedUserpkGroupId(group.getGroupId());
			for(GroupRelatedUser groupRelatedUser : groupRelatedUsers){
				userMap.remove(groupRelatedUser.getGroupRelatedUserpk().getUserId());
			}
		}
		return new ArrayList<User>(userMap.values());
	}

	@Override
	public MessageBoard getMessageBoard(MessageBoard messageBoard) throws BusinessException{
		List<MessageBoardRelatedGroup> messageBoardRelatedGroups =
				messageBoardRelatedGroupRepository
				.findByMessageBoardRelatedGrouppkMessageBoardId(messageBoard.getMessageBoardId());
		if(messageBoardRelatedGroups.size() != 1){
			throw new BusinessException("messageService.error.0001");
		}
		MessageBoard targetMessageBoard = messageBoardRepository.findOne(
				messageBoard.getMessageBoardId());
		Group group = groupRepository.findOne(
				messageBoardRelatedGroups.get(0).getMessageBoardRelatedGrouppk().getGroupId());
		List<GroupRelatedUser> groupRelatedUsers = groupRelatedUserRepository
				.findByGroupRelatedUserpkGroupId(group.getGroupId());
		List<String> userIds = new ArrayList<String>();
		for(GroupRelatedUser groupRelatedUser : groupRelatedUsers){
			userIds.add(groupRelatedUser.getGroupRelatedUserpk().getUserId());
		}
		group.setUsers(userRepository.findByUserIdIn(userIds));
		targetMessageBoard.setGroup(group);
		return targetMessageBoard;
	}

	@Override
	public Message saveMessage(Message inputMessage) {
		Long messageBoardId = inputMessage.getMessagepk().getMessageBoardId();
		long maxMessageNo = 0;
		if(0 != messageRepository.countByMessagepkMessageBoardId(messageBoardId)){
			maxMessageNo = messageRepository
				.findTopByMessageBoardIdOrderByMeesageNo(messageBoardId);
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
	public MessageBoard saveMessageBoard(Group group) {
		MessageBoard messageBoard = MessageBoard.builder()
				.title(group.getGroupName()).build();
		return saveMessageBoard(messageBoard, group);
	}

	@Override
	public MessageBoard saveMessageBoard(MessageBoard messageBoard, Group group) {

		Long newGroupId = Long.valueOf(0);
		if(0 != groupRepository.count()){
			newGroupId = groupRepository.findTopByOrderByGroupId()+1;
		}
		Group addGroup = Group.builder()
				.groupId(newGroupId)
				.groupName(group.getGroupName())
				.ver(0)
				.lastUpdatedDate(new Date())
				.build();
		groupRepository.save(addGroup);

		Long newMessageBoardId = Long.valueOf(0);
		if(0 != messageBoardRepository.count()){
			newMessageBoardId = messageBoardRepository.findTopByOrderByMessageBoardId()+1;
		}
			
		MessageBoard addMessageBoard = MessageBoard.builder()
				.messageBoardId(newMessageBoardId)
				.title(messageBoard.getTitle())
				.ver(0)
				.lastUpdatedDate(new Date())
				.build();
		messageBoardRepository.save(addMessageBoard);
		addMessageBoard.setGroup(addGroup);

		List<UserRelatedGroup> userRelatedGroups = new ArrayList<UserRelatedGroup>();
		List<GroupRelatedUser> groupRelatedUsers = new ArrayList<GroupRelatedUser>();
		for(User user : group.getUsers()){
			UserRelatedGroup addUserRelatedGroup = UserRelatedGroup.builder()
					.userRelatedGrouppk(UserRelatedGroupPK.builder()
							.groupId(newGroupId).userId(user.getUserId())
							.build())
					.ver(0)
					.lastUpdatedDate(new Date())
					.build();
			userRelatedGroups.add(addUserRelatedGroup);
			GroupRelatedUser addGroupRelatedUser = GroupRelatedUser.builder()
					.groupRelatedUserpk(GroupRelatedUserPK.builder()
							.groupId(newGroupId).userId(user.getUserId())
							.build())
					.ver(0)
					.lastUpdatedDate(new Date())
					.build();
			groupRelatedUsers.add(addGroupRelatedUser);
		}
		userRelatedGroupRepository.save(userRelatedGroups);
		groupRelatedUserRepository.save(groupRelatedUsers);
		
		GroupRelatedMessageBoard groupRelatedMessageBoard = GroupRelatedMessageBoard.builder()
				.groupRelatedMessageBoardpk(GroupRelatedMessageBoardPK.builder()
						.groupId(newGroupId).messageBoardId(newMessageBoardId).build())
				.ver(0)
				.lastUpdatedDate(new Date())
				.build();
		groupRelatedMessageBoardRepository.save(groupRelatedMessageBoard);
		
		MessageBoardRelatedGroup messageBoardRelatedGroup = MessageBoardRelatedGroup.builder()
				.messageBoardRelatedGrouppk(MessageBoardRelatedGroupPK.builder()
						.messageBoardId(newMessageBoardId).groupId(newGroupId).build())
				.ver(0)
				.lastUpdatedDate(new Date())
				.build();
		messageBoardRelatedGroupRepository.save(messageBoardRelatedGroup);
		
		return addMessageBoard;

	}

	@Override
	public MessageBoard updateMessageBoard(
			MessageBoard messageBoard, List<User> addUsers, List<User> deleteUsers) {
		
		Long messageBoardId = messageBoard.getMessageBoardId();
		MessageBoard updateMessageBoard = messageBoardRepository.findOne(messageBoardId);
		
		List<MessageBoardRelatedGroup> messageBoardRelatedGroups =
				messageBoardRelatedGroupRepository
				.findByMessageBoardRelatedGrouppkMessageBoardId(messageBoardId);
		
		Group updateGroup = groupRepository.findOne(
				messageBoardRelatedGroups.get(0).getMessageBoardRelatedGrouppk().getGroupId());
		
		if(null != deleteUsers && deleteUsers.size() != 0){
			
			List<UserRelatedGroup> deleteUserRelatedGroups = new ArrayList<UserRelatedGroup>();
			List<GroupRelatedUser> deleteGroupRelatedUsers = new ArrayList<GroupRelatedUser>();

			for(User deleteUser : deleteUsers){
				deleteUserRelatedGroups.add(UserRelatedGroup.builder().userRelatedGrouppk(
					UserRelatedGroupPK.builder()
					.groupId(updateGroup.getGroupId())
					.userId(deleteUser.getUserId())
					.build())
					.ver(0)
					.lastUpdatedDate(new Date())
					.build());
				deleteGroupRelatedUsers.add(GroupRelatedUser.builder().groupRelatedUserpk(
					GroupRelatedUserPK.builder()
					.userId(deleteUser.getUserId())
					.groupId(updateGroup.getGroupId())
					.build())
					.ver(0)
					.lastUpdatedDate(new Date())
					.build());
			}
			userRelatedGroupRepository.delete(deleteUserRelatedGroups);
			groupRelatedUserRepository.delete(deleteGroupRelatedUsers);
		}

		if(null != addUsers && addUsers.size() != 0){
			
			List<UserRelatedGroup> addUserRelatedGroups = new ArrayList<UserRelatedGroup>();
			List<GroupRelatedUser> addGroupRelatedUsers = new ArrayList<GroupRelatedUser>();

			for(User addUser : addUsers){
				addUserRelatedGroups.add(UserRelatedGroup.builder().userRelatedGrouppk(
					UserRelatedGroupPK.builder()
					.groupId(updateGroup.getGroupId())
					.userId(addUser.getUserId())
					.build())
					.ver(0)
					.lastUpdatedDate(new Date())
					.build());
				addGroupRelatedUsers.add(GroupRelatedUser.builder().groupRelatedUserpk(
					GroupRelatedUserPK.builder()
					.userId(addUser.getUserId())
					.groupId(updateGroup.getGroupId())
					.build())
					.ver(0)
					.lastUpdatedDate(new Date())
					.build());
			}

			userRelatedGroupRepository.save(addUserRelatedGroups);
			groupRelatedUserRepository.save(addGroupRelatedUsers);

		}

		if(!updateMessageBoard.getTitle().equals(messageBoard.getTitle())){
			
			updateMessageBoard.setTitle(messageBoard.getTitle());
			updateMessageBoard.setVer(updateMessageBoard.getVer()+1);
			updateGroup.setGroupName(messageBoard.getTitle());
			updateGroup.setVer(updateGroup.getVer()+1);
		
			messageBoardRepository.save(updateMessageBoard);
			groupRepository.save(updateGroup);
		
		}

		return updateMessageBoard;

	}

	@Override
	public MessageBoard deleteMessageBoard(MessageBoard messageBoard) {

		Long messageBoardId = messageBoard.getMessageBoardId();
		MessageBoard deleteMessageBoard = messageBoardRepository.findOne(messageBoardId);
		
		List<MessageBoardRelatedGroup> messageBoardRelatedGroups =
				messageBoardRelatedGroupRepository
				.findByMessageBoardRelatedGrouppkMessageBoardId(messageBoardId);

		Group deleteGroup = groupRepository.findOne(
				messageBoardRelatedGroups.get(0).getMessageBoardRelatedGrouppk().getGroupId());
		
		List<UserRelatedGroup> deleteUserRelatedGroups = new ArrayList<UserRelatedGroup>();
		List<GroupRelatedUser> deleteGroupRelatedUsers = new ArrayList<GroupRelatedUser>();
		
		for(GroupRelatedUser groupRelatedUser : 
			groupRelatedUserRepository.findByGroupRelatedUserpkGroupId(deleteGroup.getGroupId())){
			deleteUserRelatedGroups.add(UserRelatedGroup.builder().userRelatedGrouppk(
					UserRelatedGroupPK.builder()
					.groupId(deleteGroup.getGroupId())
					.userId(groupRelatedUser.getGroupRelatedUserpk().getUserId())
					.build())
					.build());
			deleteGroupRelatedUsers.add(groupRelatedUser);
		}
		
		groupRelatedUserRepository.delete(deleteGroupRelatedUsers);
		userRelatedGroupRepository.delete(deleteUserRelatedGroups);
		
		groupRelatedMessageBoardRepository.delete(
				GroupRelatedMessageBoard.builder()
				.groupRelatedMessageBoardpk(GroupRelatedMessageBoardPK.builder()
						.groupId(deleteGroup.getGroupId())
						.messageBoardId(messageBoardId)
						.build())
				.build());
		messageBoardRelatedGroupRepository.delete(
				MessageBoardRelatedGroup.builder()
				.messageBoardRelatedGrouppk(MessageBoardRelatedGroupPK.builder()
						.messageBoardId(messageBoardId)
						.groupId(deleteGroup.getGroupId())
						.build())
				.build());
		
		groupRepository.delete(deleteGroup);
		deleteMessages(deleteMessageBoard);
		messageBoardRepository.delete(deleteMessageBoard);
		
		return deleteMessageBoard;

	}


	@Override
	public Message updateMessage(Message message) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public User saveUser(User user) {
		userRepository.save(user);
		return user;
	}

	@Override
	public User updateUser(User user) {
		User updateTargetUser = userRepository.findOne(user.getUserId());
		if(!updateTargetUser.getFirstName().equals(user.getFirstName())){
			updateTargetUser.setFirstName(user.getFirstName());
			updateTargetUser.setLastUpdatedDate(new Date());
		}
		if(!updateTargetUser.getLastName().equals(user.getLastName())){
			updateTargetUser.setLastName(user.getLastName());
			updateTargetUser.setLastUpdatedDate(new Date());
		}
		if(!updateTargetUser.getLoginId().equals(user.getLoginId())){
			updateTargetUser.setLoginId(user.getLoginId());
			updateTargetUser.setLastUpdatedDate(new Date());
		}
		if(!updateTargetUser.getImageFilePath().equals(user.getImageFilePath())){
			updateTargetUser.setImageFilePath(user.getImageFilePath());
			updateTargetUser.setLastUpdatedDate(new Date());
		}
		if(null != user.getAuthorityLevel()){
			if(updateTargetUser.getAuthorityLevel().intValue() 
					!= user.getAuthorityLevel().intValue()){
				updateTargetUser.setAuthorityLevel(user.getAuthorityLevel());
				updateTargetUser.setLastUpdatedDate(new Date());
			}
		}
		if(updateTargetUser.isBrideSide() != user.isBrideSide()){
			updateTargetUser.setBrideSide(user.isBrideSide());
				updateTargetUser.setLastUpdatedDate(new Date());
		}
		userRepository.save(updateTargetUser);
		return updateTargetUser;
	}

	@Override
	public User deleteUser(User user) {
		List<UserRelatedGroup> userRelatedGroups = userRelatedGroupRepository
				.findByUserRelatedGrouppkUserId(user.getUserId());
		if(userRelatedGroups.size() != 0){
			List<GroupRelatedUser> deleteGroupRelatedUsers = new ArrayList<GroupRelatedUser>();
			List<UserRelatedGroup> deleteUserRelatedGroups = new ArrayList<UserRelatedGroup>();
			for(UserRelatedGroup userRelatedGroup : 
				userRelatedGroupRepository.findByUserRelatedGrouppkUserId(user.getUserId())){
				deleteGroupRelatedUsers.add(GroupRelatedUser.builder().groupRelatedUserpk(
						GroupRelatedUserPK.builder()
						.userId(user.getUserId())
						.groupId(userRelatedGroup.getUserRelatedGrouppk().getGroupId())
						.build())
						.build());
				deleteUserRelatedGroups.add(userRelatedGroup);
			}
			groupRelatedUserRepository.delete(deleteGroupRelatedUsers);
			userRelatedGroupRepository.delete(deleteUserRelatedGroups);
		}		
		
		userRepository.delete(user);

		return user;

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

	@Override
	public void deleteMessages(MessageBoard messageBoard) {
		List<Message> messages = messageRepository
				.findByMessagepkMessageBoardId(messageBoard.getMessageBoardId());
		messageRepository.delete(messages);
	}

	@Override
	public User getUser(String userId) {
		return userRepository.findOne(userId);
	}

}
