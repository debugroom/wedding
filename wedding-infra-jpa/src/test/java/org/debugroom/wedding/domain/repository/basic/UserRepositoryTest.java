package org.debugroom.wedding.domain.repository.basic;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

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
import org.debugroom.wedding.domain.repository.basic.UserRepository;

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
	     public static class WhiteBoxTest{
			 
			 @Inject
			 @Named("userRepository")
			 UserRepository userRepository;
			 
	         @Before
	         public void setUp() throws Exception{
	        	 new TestContextManager(getClass()).prepareTestInstance(this);
	         }
	         
	         @Theory
	         @Category(TestsWithDatabaseAccess.class)
	         public void normalTestCase1_findOneForUser(FindUserFixture fixture){
	        	 
	        	 User user = userRepository.findOne(fixture.userId);
	        	 
	        	 assertThat(fixture.toString(), user.getUserName(), is(fixture.expected.getUserName()));

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
