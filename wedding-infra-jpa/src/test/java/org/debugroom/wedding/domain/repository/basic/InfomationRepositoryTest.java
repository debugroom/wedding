package org.debugroom.wedding.domain.repository.basic;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;
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
import org.debugroom.wedding.domain.model.entity.Infomation;
import org.debugroom.wedding.domain.repository.basic.InfomationRepository;
import org.debugroom.wedding.domain.repository.basic.InfomationRepositoryTest.UnitTest.FindInfomationByUserIdTest.FindInfomationByUserIdAndReleaseDateLessThanFixture.FindInfomationByUserIdAndReleaseDateLessThanFixtureBuilder;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;


/**
 * 
 * @author org.debugroom
 *
 */
@RunWith(Enclosed.class)
public class InfomationRepositoryTest {

	@Category(UnitTests.class)
	@RunWith(Enclosed.class)
	public static class UnitTest{
	
		@RunWith(Theories.class)
		@ContextConfiguration("classpath:META-INF/spring/wedding-test.xml")
		public static class FindInfomationByUserIdTest{
		
			@Inject
			InfomationRepository infomationRepository;
		
			@Before 
			public void setUp() throws Exception{
				new TestContextManager(getClass()).prepareTestInstance(this);
				
				Calendar calendar = Calendar.getInstance();
				calendar.set(Calendar.YEAR, 2015);
				calendar.set(Calendar.MONTH, 7);
				calendar.set(Calendar.DAY_OF_MONTH, 1);

				List<Infomation> case1ExpectedInfomationList = new ArrayList<Infomation>();
				findInfomationFixture[0].setCurrentTime(calendar.getTime());
				findInfomationFixture[0].expected.addAll(case1ExpectedInfomationList);
				
				List<Infomation> case2ExpectedInfomationList = new ArrayList<Infomation>();
				case2ExpectedInfomationList.add(Infomation.builder().infoId("00000000").build());
				case2ExpectedInfomationList.add(Infomation.builder().infoId("00000001").build());
				case2ExpectedInfomationList.add(Infomation.builder().infoId("00000002").build());
				case2ExpectedInfomationList.add(Infomation.builder().infoId("00000003").build());
				case2ExpectedInfomationList.add(Infomation.builder().infoId("00000004").build());
				case2ExpectedInfomationList.add(Infomation.builder().infoId("00000007").build());
				case2ExpectedInfomationList.add(Infomation.builder().infoId("00000008").build());
				case2ExpectedInfomationList.add(Infomation.builder().infoId("00000010").build());
				case2ExpectedInfomationList.add(Infomation.builder().infoId("00000011").build());
				case2ExpectedInfomationList.add(Infomation.builder().infoId("00000013").build());
				case2ExpectedInfomationList.add(Infomation.builder().infoId("00000015").build());
				findInfomationFixture[1].setCurrentTime(calendar.getTime());
				findInfomationFixture[1].expected.addAll(case2ExpectedInfomationList);
				
				List<Infomation> case3ExpectedInfomationList = new ArrayList<Infomation>();
				case3ExpectedInfomationList.add(Infomation.builder().infoId("00000000").build());
				case3ExpectedInfomationList.add(Infomation.builder().infoId("00000001").build());
				case3ExpectedInfomationList.add(Infomation.builder().infoId("00000002").build());
				case3ExpectedInfomationList.add(Infomation.builder().infoId("00000003").build());
				case3ExpectedInfomationList.add(Infomation.builder().infoId("00000004").build());
				case3ExpectedInfomationList.add(Infomation.builder().infoId("00000008").build());
				case3ExpectedInfomationList.add(Infomation.builder().infoId("00000010").build());
				case3ExpectedInfomationList.add(Infomation.builder().infoId("00000012").build());
				case3ExpectedInfomationList.add(Infomation.builder().infoId("00000014").build());
				case3ExpectedInfomationList.add(Infomation.builder().infoId("00000015").build());
				findInfomationFixture[2].setCurrentTime(calendar.getTime());
				findInfomationFixture[2].expected.addAll(case3ExpectedInfomationList);
				
				List<Infomation> case4ExpectedInfomationList = new ArrayList<Infomation>();
				case4ExpectedInfomationList.add(Infomation.builder().infoId("00000000").build());
				case4ExpectedInfomationList.add(Infomation.builder().infoId("00000002").build());
				case4ExpectedInfomationList.add(Infomation.builder().infoId("00000004").build());
				case4ExpectedInfomationList.add(Infomation.builder().infoId("00000005").build());
				case4ExpectedInfomationList.add(Infomation.builder().infoId("00000006").build());
				case4ExpectedInfomationList.add(Infomation.builder().infoId("00000008").build());
				findInfomationFixture[3].setCurrentTime(calendar.getTime());
				findInfomationFixture[3].expected.addAll(case4ExpectedInfomationList);

			}
			
			@DataPoints
			public static FindInfomationByUserIdAndReleaseDateLessThanFixture[] findInfomationFixture = {
					new FindInfomationByUserIdAndReleaseDateLessThanFixtureBuilder()
							.userId("00000000")
							.expected((List<Infomation>)new ArrayList<Infomation>())
							.build(),
					new FindInfomationByUserIdAndReleaseDateLessThanFixtureBuilder()
							.userId("00000001")
							.expected((List<Infomation>)new ArrayList<Infomation>())
							.build(),
					new FindInfomationByUserIdAndReleaseDateLessThanFixtureBuilder()
							.userId("00000002")
							.expected((List<Infomation>)new ArrayList<Infomation>())
							.build(),
					new FindInfomationByUserIdAndReleaseDateLessThanFixtureBuilder()
							.userId("00000003")
							.expected((List<Infomation>)new ArrayList<Infomation>())
							.build()
			};
			
			@Theory
			@Category(TestsWithDatabaseAccess.class)
			public void normalTestCase_findInfomationByUserIdAndReleaseDateLessThan(
					FindInfomationByUserIdAndReleaseDateLessThanFixture fixture){
				
				List<Infomation> infomationList = infomationRepository
						.findUserByUserIdAndReleaseDateLessThan(fixture.userId, fixture.currentTime);
				
				for(int i=0 ; i < infomationList.size() ; i++){
					assertThat(fixture.toString(), infomationList.get(i).getInfoId(),
							is(fixture.expected.get(i).getInfoId()));
				}
			}

			@AllArgsConstructor
			@Builder
			@Data
			public static class FindInfomationByUserIdAndReleaseDateLessThanFixture{
				String userId;
				Date currentTime;
				List<Infomation> expected = new ArrayList<Infomation>();
	            @Override
	            public String toString(){
	                return String.format("when UserId = %s, Date = %s, expceted = %s ", 
	                		userId, currentTime.toString(), expected);
	             }
			}
		}
	}
}
