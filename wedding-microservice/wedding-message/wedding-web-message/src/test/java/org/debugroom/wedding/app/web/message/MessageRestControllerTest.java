package org.debugroom.wedding.app.web.message;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.cassandra.core.CassandraAdminOperations;
import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.ResultActions.*;

import org.debugroom.wedding.config.WebApp;
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

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = WebApp.class)
@WebAppConfiguration
@ActiveProfiles({"dev", "cassandra"})
public class MessageRestControllerTest {

	@Autowired
	WebApplicationContext context;
	
	@Autowired
	@Qualifier("cassandraTemplate")
	CassandraOperations operations;
	@Autowired
	@Qualifier("cassandraAdminOperations")
	CassandraAdminOperations adminOperations;
	
	MockMvc mockMvc;
	
	@Before
	public void setUpMockMvc(){
		this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
		
		operations.truncate("group");
		operations.truncate("group_related_message_board");
		operations.truncate("group_related_user");
		operations.truncate("message");
		operations.truncate("message_board");
		operations.truncate("message_board_related_group");
		operations.truncate("users");
		operations.truncate("user_related_group");

		Group groupA = Group.builder().groupId(Long.valueOf(0)).groupName("GroupA").ver(0).lastUpdatedDate(new Date()).build();
		Group groupB = Group.builder().groupId(Long.valueOf(1)).groupName("GroupB").ver(0).lastUpdatedDate(new Date()).build();
		Group groupC = Group.builder().groupId(Long.valueOf(2)).groupName("GroupC").ver(0).lastUpdatedDate(new Date()).build();
		Group groupD = Group.builder().groupId(Long.valueOf(3)).groupName("GroupD").ver(0).lastUpdatedDate(new Date()).build();
		
		User user0 = User.builder()
				.userId("00000000")
				.lastName("(ΦωΦ)")
				.firstName("フフフ")
				.loginId("test0")
				.authorityLevel(0)
				.isBrideSide(false)
				.imageFilePath("resources/app/img/debugroom.png")
				.ver(0)
				.build();

		User user1 = User.builder()
				.userId("00000001")
				.lastName("(・∀・)")
				.firstName("カエレ")
				.loginId("test1")
				.authorityLevel(9)
				.isBrideSide(false)
				.imageFilePath("resources/app/img/konyagayamada.png")
				.ver(0)
				.build();

		User user2 = User.builder()
				.userId("00000002")
				.lastName("(・ω・`)")
				.firstName("しょぼーん")
				.loginId("test2")
				.authorityLevel(9)
				.isBrideSide(false)
				.imageFilePath("resources/app/img/honyagayamada.png")
				.ver(0)
				.build();

		User user3 = User.builder()
				.userId("00000003")
				.lastName("org")
				.firstName("debugroom")
				.loginId("test3")
				.authorityLevel(9)
				.isBrideSide(false)
				.imageFilePath("resources/app/img/joushigayamada.png")
				.ver(0)
				.build();

		MessageBoard messageBoardA = MessageBoard.builder()
				.messageBoardId(Long.valueOf(0))
				.title("メッセージ板A")
				.ver(0)
				.lastUpdatedDate(new Date())
				.build();
		MessageBoard messageBoardB = MessageBoard.builder()
				.messageBoardId(Long.valueOf(1))
				.title("メッセージ板B")
				.ver(0)
				.lastUpdatedDate(new Date())
				.build();
		MessageBoard messageBoardC = MessageBoard.builder()
				.messageBoardId(Long.valueOf(2))
				.title("メッセージ板C")
				.ver(0)
				.lastUpdatedDate(new Date())
				.build();
		MessageBoard messageBoardD = MessageBoard.builder()
				.messageBoardId(Long.valueOf(3))
				.title("メッセージ板D")
				.ver(0)
				.lastUpdatedDate(new Date())
				.build();
		MessageBoard messageBoardE = MessageBoard.builder()
				.messageBoardId(Long.valueOf(4))
				.title("メッセージ板E")
				.ver(0)
				.lastUpdatedDate(new Date())
				.build();
		MessageBoard messageBoardF = MessageBoard.builder()
				.messageBoardId(Long.valueOf(5))
				.title("メッセージ板F")
				.ver(0)
				.lastUpdatedDate(new Date())
				.build();


		Message messageA1 = Message.builder()
				.messagepk(MessagePK.builder().messageBoardId(Long.valueOf(0)).messageNo(Long.valueOf(0)).build())
				.comment("(ΦωΦ)フフフA1")
				.userId("00000000")
				.ver(0)
				.lastUpdatedDate(new Date())
				.build();
		Message messageA2 = Message.builder()
				.messagepk(MessagePK.builder().messageBoardId(Long.valueOf(0)).messageNo(Long.valueOf(1)).build())
				.comment("(ΦωΦ)フフフA2")
				.userId("00000001")
				.ver(0)
				.lastUpdatedDate(new Date())
				.build();
		Message messageA3 = Message.builder()
				.messagepk(MessagePK.builder().messageBoardId(Long.valueOf(0)).messageNo(Long.valueOf(2)).build())
				.comment("(ΦωΦ)フフフA3")
				.userId("00000002")
				.ver(0)
				.lastUpdatedDate(new Date())
				.build();
		Message messageB1 = Message.builder()
				.messagepk(MessagePK.builder().messageBoardId(Long.valueOf(1)).messageNo(Long.valueOf(0)).build())
				.comment("(ΦωΦ)フフフB1")
				.userId("00000000")
				.ver(0)
				.lastUpdatedDate(new Date())
				.build();
		Message messageB2 = Message.builder()
				.messagepk(MessagePK.builder().messageBoardId(Long.valueOf(1)).messageNo(Long.valueOf(1)).build())
				.comment("(ΦωΦ)フフフB2")
				.userId("00000001")
				.ver(0)
				.lastUpdatedDate(new Date())
				.build();
		Message messageB3 = Message.builder()
				.messagepk(MessagePK.builder().messageBoardId(Long.valueOf(1)).messageNo(Long.valueOf(2)).build())
				.comment("(ΦωΦ)フフフB3")
				.userId("00000003")
				.ver(0)
				.lastUpdatedDate(new Date())
				.build();
		Message messageC1 = Message.builder()
				.messagepk(MessagePK.builder().messageBoardId(Long.valueOf(2)).messageNo(Long.valueOf(0)).build())
				.comment("(ΦωΦ)フフフC1")
				.userId("00000000")
				.ver(0)
				.lastUpdatedDate(new Date())
				.build();
		Message messageC2 = Message.builder()
				.messagepk(MessagePK.builder().messageBoardId(Long.valueOf(2)).messageNo(Long.valueOf(1)).build())
				.comment("(ΦωΦ)フフフC2")
				.userId("00000002")
				.ver(0)
				.lastUpdatedDate(new Date())
				.build();
		Message messageC3 = Message.builder()
				.messagepk(MessagePK.builder().messageBoardId(Long.valueOf(2)).messageNo(Long.valueOf(2)).build())
				.comment("(ΦωΦ)フフフC3")
				.userId("00000003")
				.ver(0)
				.lastUpdatedDate(new Date())
				.build();
		Message messageE1 = Message.builder()
				.messagepk(MessagePK.builder().messageBoardId(Long.valueOf(2)).messageNo(Long.valueOf(2)).build())
				.comment("(ΦωΦ)フフフE1")
				.userId("00000001")
				.ver(0)
				.lastUpdatedDate(new Date())
				.build();
		Message messageE2 = Message.builder()
				.messagepk(MessagePK.builder().messageBoardId(Long.valueOf(2)).messageNo(Long.valueOf(2)).build())
				.userId("00000002")
				.comment("(ΦωΦ)フフフE2")
				.ver(0)
				.lastUpdatedDate(new Date())
				.build();

		UserRelatedGroup userRelatedGroupA0 = UserRelatedGroup.builder()
				.userRelatedGrouppk(UserRelatedGroupPK.builder()
						.groupId(Long.valueOf(0)).userId("00000000").build())
				.ver(0)
				.lastUpdatedDate(new Date())
				.build();


		UserRelatedGroup userRelatedGroupA1 = UserRelatedGroup.builder()
				.userRelatedGrouppk(UserRelatedGroupPK.builder()
						.groupId(Long.valueOf(0)).userId("00000001").build())
				.ver(0)
				.lastUpdatedDate(new Date())
				.build();


		UserRelatedGroup userRelatedGroupB1 = UserRelatedGroup.builder()
				.userRelatedGrouppk(UserRelatedGroupPK.builder()
						.groupId(Long.valueOf(1)).userId("00000001").build())
				.ver(0)
				.lastUpdatedDate(new Date())
				.build();

		UserRelatedGroup userRelatedGroupB2 = UserRelatedGroup.builder()
				.userRelatedGrouppk(UserRelatedGroupPK.builder()
						.groupId(Long.valueOf(1)).userId("00000002").build())
				.ver(0)
				.lastUpdatedDate(new Date())
				.build();

		UserRelatedGroup userRelatedGroupC2 = UserRelatedGroup.builder()
				.userRelatedGrouppk(UserRelatedGroupPK.builder()
						.groupId(Long.valueOf(2)).userId("00000002").build())
				.ver(0)
				.lastUpdatedDate(new Date())
				.build();

		UserRelatedGroup userRelatedGroupC3 = UserRelatedGroup.builder()
				.userRelatedGrouppk(UserRelatedGroupPK.builder()
						.groupId(Long.valueOf(2)).userId("00000003").build())
				.ver(0)
				.lastUpdatedDate(new Date())
				.build();

		UserRelatedGroup userRelatedGroupD3 = UserRelatedGroup.builder()
				.userRelatedGrouppk(UserRelatedGroupPK.builder()
						.groupId(Long.valueOf(3)).userId("00000003").build())
				.ver(0)
				.lastUpdatedDate(new Date())
				.build();

		UserRelatedGroup userRelatedGroupD0 = UserRelatedGroup.builder()
				.userRelatedGrouppk(UserRelatedGroupPK.builder()
						.groupId(Long.valueOf(3)).userId("00000000").build())
				.ver(0)
				.lastUpdatedDate(new Date())
				.build();

		GroupRelatedUser groupRelatedUser0A = GroupRelatedUser.builder()
				.groupRelatedUserpk(GroupRelatedUserPK.builder()
						.userId("00000000").groupId(Long.valueOf(0)).build())
				.ver(0)
				.lastUpdatedDate(new Date())
				.build();

		GroupRelatedUser groupRelatedUser1A = GroupRelatedUser.builder()
				.groupRelatedUserpk(GroupRelatedUserPK.builder()
						.userId("00000001").groupId(Long.valueOf(0)).build())
				.ver(0)
				.lastUpdatedDate(new Date())
				.build();

		GroupRelatedUser groupRelatedUser1B = GroupRelatedUser.builder()
				.groupRelatedUserpk(GroupRelatedUserPK.builder()
						.userId("00000001").groupId(Long.valueOf(1)).build())
				.ver(0)
				.lastUpdatedDate(new Date())
				.build();

		GroupRelatedUser groupRelatedUser2B = GroupRelatedUser.builder()
				.groupRelatedUserpk(GroupRelatedUserPK.builder()
						.userId("00000002").groupId(Long.valueOf(1)).build())
				.ver(0)
				.lastUpdatedDate(new Date())
				.build();

		GroupRelatedUser groupRelatedUser2C = GroupRelatedUser.builder()
				.groupRelatedUserpk(GroupRelatedUserPK.builder()
						.userId("00000002").groupId(Long.valueOf(2)).build())
				.ver(0)
				.lastUpdatedDate(new Date())
				.build();

		GroupRelatedUser groupRelatedUser3C = GroupRelatedUser.builder()
				.groupRelatedUserpk(GroupRelatedUserPK.builder()
						.userId("00000003").groupId(Long.valueOf(2)).build())
				.ver(0)
				.lastUpdatedDate(new Date())
				.build();

		GroupRelatedUser groupRelatedUser3D = GroupRelatedUser.builder()
				.groupRelatedUserpk(GroupRelatedUserPK.builder()
						.userId("00000003").groupId(Long.valueOf(3)).build())
				.ver(0)
				.lastUpdatedDate(new Date())
				.build();

		GroupRelatedUser groupRelatedUser0D = GroupRelatedUser.builder()
				.groupRelatedUserpk(GroupRelatedUserPK.builder()
						.userId("00000000").groupId(Long.valueOf(3)).build())
				.ver(0)
				.lastUpdatedDate(new Date())
				.build();

		GroupRelatedMessageBoard groupRelatedMessageBoardA0 = GroupRelatedMessageBoard.builder()
				.groupRelatedMessageBoardpk(GroupRelatedMessageBoardPK.builder()
						.groupId(Long.valueOf(0)).messageBoardId(Long.valueOf(0)).build())
				.ver(0)
				.lastUpdatedDate(new Date())
				.build();

		GroupRelatedMessageBoard groupRelatedMessageBoardA4 = GroupRelatedMessageBoard.builder()
				.groupRelatedMessageBoardpk(GroupRelatedMessageBoardPK.builder()
						.groupId(Long.valueOf(0)).messageBoardId(Long.valueOf(4)).build())
				.ver(0)
				.lastUpdatedDate(new Date())
				.build();

		GroupRelatedMessageBoard groupRelatedMessageBoardA5 = GroupRelatedMessageBoard.builder()
				.groupRelatedMessageBoardpk(GroupRelatedMessageBoardPK.builder()
						.groupId(Long.valueOf(0)).messageBoardId(Long.valueOf(5)).build())
				.ver(0)
				.lastUpdatedDate(new Date())
				.build();

		GroupRelatedMessageBoard groupRelatedMessageBoardB1 = GroupRelatedMessageBoard.builder()
				.groupRelatedMessageBoardpk(GroupRelatedMessageBoardPK.builder()
						.groupId(Long.valueOf(1)).messageBoardId(Long.valueOf(1)).build())
				.ver(0)
				.lastUpdatedDate(new Date())
				.build();

		GroupRelatedMessageBoard groupRelatedMessageBoardC2 = GroupRelatedMessageBoard.builder()
				.groupRelatedMessageBoardpk(GroupRelatedMessageBoardPK.builder()
						.groupId(Long.valueOf(2)).messageBoardId(Long.valueOf(2)).build())
				.ver(0)
				.lastUpdatedDate(new Date())
				.build();


		GroupRelatedMessageBoard groupRelatedMessageBoardD3 = GroupRelatedMessageBoard.builder()
				.groupRelatedMessageBoardpk(GroupRelatedMessageBoardPK.builder()
						.groupId(Long.valueOf(3)).messageBoardId(Long.valueOf(3)).build())
				.ver(0)
				.lastUpdatedDate(new Date())
				.build();

		MessageBoardRelatedGroup messageBoardRelatedGroup0A = MessageBoardRelatedGroup.builder()
				.messageBoardRelatedGrouppk(MessageBoardRelatedGroupPK.builder()
						.messageBoardId(Long.valueOf(0)).groupId(Long.valueOf(0)).build())
				.ver(0)
				.lastUpdatedDate(new Date())
				.build();

		MessageBoardRelatedGroup messageBoardRelatedGroup4A = MessageBoardRelatedGroup.builder()
				.messageBoardRelatedGrouppk(MessageBoardRelatedGroupPK.builder()
						.messageBoardId(Long.valueOf(4)).groupId(Long.valueOf(0)).build())
				.ver(0)
				.lastUpdatedDate(new Date())
				.build();

		MessageBoardRelatedGroup messageBoardRelatedGroup5A = MessageBoardRelatedGroup.builder()
				.messageBoardRelatedGrouppk(MessageBoardRelatedGroupPK.builder()
						.messageBoardId(Long.valueOf(5)).groupId(Long.valueOf(0)).build())
				.ver(0)
				.lastUpdatedDate(new Date())
				.build();

		MessageBoardRelatedGroup messageBoardRelatedGroup1B = MessageBoardRelatedGroup.builder()
				.messageBoardRelatedGrouppk(MessageBoardRelatedGroupPK.builder()
						.messageBoardId(Long.valueOf(1)).groupId(Long.valueOf(1)).build())
				.ver(0)
				.lastUpdatedDate(new Date())
				.build();

		MessageBoardRelatedGroup messageBoardRelatedGroup2C = MessageBoardRelatedGroup.builder()
				.messageBoardRelatedGrouppk(MessageBoardRelatedGroupPK.builder()
						.messageBoardId(Long.valueOf(2)).groupId(Long.valueOf(2)).build())
				.ver(0)
				.lastUpdatedDate(new Date())
				.build();

		MessageBoardRelatedGroup messageBoardRelatedGroup3D = MessageBoardRelatedGroup.builder()
				.messageBoardRelatedGrouppk(MessageBoardRelatedGroupPK.builder()
						.messageBoardId(Long.valueOf(3)).groupId(Long.valueOf(3)).build())
				.ver(0)
				.lastUpdatedDate(new Date())
				.build();

		operations.insert(groupA);
		operations.insert(groupB);
		operations.insert(groupC);
		operations.insert(groupD);
		operations.insert(user0);
		operations.insert(user1);
		operations.insert(user2);
		operations.insert(user3);
		operations.insert(messageBoardA);
		operations.insert(messageBoardB);
		operations.insert(messageBoardC);
		operations.insert(messageBoardD);
		operations.insert(messageBoardE);
		operations.insert(messageBoardF);
		operations.insert(messageA1);
		operations.insert(messageA2);
		operations.insert(messageA3);
		operations.insert(messageB1);
		operations.insert(messageB2);
		operations.insert(messageB3);
		operations.insert(messageC1);
		operations.insert(messageC2);
		operations.insert(messageC3);
		operations.insert(messageE1);
		operations.insert(messageE2);
		operations.insert(userRelatedGroupA0);
		operations.insert(userRelatedGroupA1);
		operations.insert(userRelatedGroupB1);
		operations.insert(userRelatedGroupB2);
		operations.insert(userRelatedGroupC2);
		operations.insert(userRelatedGroupC3);
		operations.insert(userRelatedGroupD3);
		operations.insert(userRelatedGroupD0);
		operations.insert(groupRelatedUser0A);
		operations.insert(groupRelatedUser1A);
		operations.insert(groupRelatedUser1B);
		operations.insert(groupRelatedUser2B);
		operations.insert(groupRelatedUser2C);
		operations.insert(groupRelatedUser3C);
		operations.insert(groupRelatedUser3D);
		operations.insert(groupRelatedUser0D);
		operations.insert(groupRelatedMessageBoardA0);
		operations.insert(groupRelatedMessageBoardA4);
		operations.insert(groupRelatedMessageBoardA5);
		operations.insert(groupRelatedMessageBoardB1);
		operations.insert(groupRelatedMessageBoardC2);
		operations.insert(groupRelatedMessageBoardD3);
		operations.insert(messageBoardRelatedGroup0A);
		operations.insert(messageBoardRelatedGroup4A);
		operations.insert(messageBoardRelatedGroup5A);
		operations.insert(messageBoardRelatedGroup1B);
		operations.insert(messageBoardRelatedGroup2C);
		operations.insert(messageBoardRelatedGroup3D);

	}
	
	@Test
	public void testGetChatPortalResource() throws Exception{
		mockMvc.perform(get("/api/v1/portal/00000001"))
		.andExpect(status().isOk())
		.andDo(log());
	}

	@Test
	public void testGetMessages() throws Exception{
		mockMvc.perform(get("/api/v1/messages/1"))
		.andExpect(status().isOk())
		.andDo(log());
	}
}
