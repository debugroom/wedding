package org.debugroom.wedding.domain.repository.common;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.util.Calendar;

import javax.inject.Inject;
import javax.inject.Named;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.experimental.runners.Enclosed;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;
import org.springframework.test.context.TestContextManager;
import org.springframework.test.context.ContextConfiguration;

import org.debugroom.framework.test.junit.category.UnitTests;
import org.debugroom.framework.test.junit.category.TestsWithDatabaseAccess;
import org.debugroom.wedding.domain.model.entity.User;
import org.debugroom.wedding.domain.repository.common.UserRepository;

import lombok.AllArgsConstructor;
import lombok.Builder;

/**
 * 
 * @author org.debugroom
 *
 */
@RunWith(Enclosed.class)
public class UserRepositoryTest {

	@Category(UnitTests.class)
	@RunWith(Enclosed.class)
	public static class UnitTest{
		
		@RunWith(Theories.class)
		@ContextConfiguration("classpath:META-INF/spring/wedding-test.xml")
		public static class CountUserTest{
			
			@Inject
			UserRepository userRepository;
			
			@Before
			public void setUp() throws Exception{
	        	 new TestContextManager(getClass()).prepareTestInstance(this);
			}

			@DataPoints
			public static CountUserFixture[] countUserFixture = {
				CountUserFixture.builder()
					.user(User.builder().userId("00000000").build())
					.expected(Long.parseLong("1"))
					.build(),
				CountUserFixture.builder()
					.user(User.builder().userId("12345678").build())
					.expected(Long.parseLong("0"))
					.build(),
			};

			@Theory
			@Category(TestsWithDatabaseAccess.class)
			public void normalTestCase1_CountByUserId(CountUserFixture fixture){

				long count = (long)userRepository.countByUserId(fixture.user.getUserId());
				
				assertThat(fixture.toString(), count, is(fixture.expected));
				
			}

			@AllArgsConstructor
			@Builder
			public static class CountUserFixture{
				User user;
				long expected;
	            @Override
	            public String toString(){
	                return String.format("when userId = %s, expceted = %s ", user.getUserId(), expected);
	             }
			}

		}
		 @RunWith(Theories.class)
	     @ContextConfiguration("classpath:META-INF/spring/wedding-test.xml")
	     public static class FindUserTest{
			 
			 @Inject
			 UserRepository userRepository;
			 
	         @Before
	         public void setUp() throws Exception{
	        	 new TestContextManager(getClass()).prepareTestInstance(this);
	         }
	         
	         @DataPoints
	         public static FindUserFixture[] findUserFixture = {
	        	 new FindUserFixture()
	        	 		.userId("00000000")
	        	 		.expected((User.builder())
	        	 							.userId("00000000")
	        	 							.loginId("org.debugroom")
	        	 							.userName("(ΦωΦ)")
	        	 							.lastLoginDate(new Calendar.Builder()
	        	 													.setDate(2015, 1, 1)
	        	 													.setTimeOfDay(0, 0, 0)
	        	 													.build()
	        	 													.getTime()
	        	 									)
	        	 							.build()
	        	 					)
	        	 
	         };
	         
	         @Theory
	         @Category(TestsWithDatabaseAccess.class)
	         public void normalTestCase1_findOneForUser(FindUserFixture fixture){
	        	 
	        	 User user = userRepository.findOne(fixture.userId);
	        	 
	        	 assertThat(fixture.toString(), user.getUserName(), 
	        			 is(fixture.expected.getUserName()));
	        	 assertThat(fixture.toString(), user.getLoginId(), 
	        			 is(fixture.expected.getLoginId()));

	         }

	         public static class FindUserFixture{
	        	 String userId;
	             User expected;
	             FindUserFixture(){};
	             FindUserFixture(String userId, User expected){
	            	 this.userId = userId;
	                 this.expected = expected;
	             }
	             public FindUserFixture userId(String userId){
	                 this.userId = userId;
	                 return this;
	             }
	                
	             public FindUserFixture expected(User expected){
	                 this.expected = expected;
	                 return this;
	             }
	                
	             @Override
	             public String toString(){
	                 return String.format("when exception = %s, expceted = %s ", userId, expected);
	             }
	         }
		 }
	}
}
