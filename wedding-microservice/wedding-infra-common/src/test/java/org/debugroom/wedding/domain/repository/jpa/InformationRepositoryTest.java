package org.debugroom.wedding.domain.repository.jpa;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.util.List;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.experimental.categories.Category;
import org.junit.experimental.runners.Enclosed;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;
import org.springframework.test.context.TestContextManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import org.debugroom.framework.test.junit.category.TestsWithDatabaseAccess;
import org.debugroom.framework.test.junit.category.UnitTests;
import org.debugroom.wedding.config.TestConfig;
import org.debugroom.wedding.domain.entity.Information;

@RunWith(Enclosed.class)
public class InformationRepositoryTest {

	@Category(UnitTests.class)
	@RunWith(Enclosed.class)
	public static class UnitTest{
	
		@RunWith(Theories.class)
		@ContextConfiguration(classes = TestConfig.class)
		@ActiveProfiles(profiles = {"jpa", "junit"})
		public static class FindInformationByUserIdTest{
		
			@Inject
			InformationRepository InformationRepository;
		
			@Before 
			public void setUp() throws Exception{
				new TestContextManager(getClass()).prepareTestInstance(this);
				
				Calendar calendar = Calendar.getInstance();
				calendar.set(Calendar.YEAR, 2016);
				calendar.set(Calendar.MONTH, 7);
				calendar.set(Calendar.DAY_OF_MONTH, 1);

				List<Information> case1ExpectedInformationList = new ArrayList<Information>();
				case1ExpectedInformationList.add(Information.builder().infoId("00000000").build());
				case1ExpectedInformationList.add(Information.builder().infoId("00000001").build());
				case1ExpectedInformationList.add(Information.builder().infoId("00000002").build());
				case1ExpectedInformationList.add(Information.builder().infoId("00000003").build());
				case1ExpectedInformationList.add(Information.builder().infoId("00000004").build());
				case1ExpectedInformationList.add(Information.builder().infoId("00000005").build());
				case1ExpectedInformationList.add(Information.builder().infoId("00000006").build());
				case1ExpectedInformationList.add(Information.builder().infoId("00000007").build());
				case1ExpectedInformationList.add(Information.builder().infoId("00000008").build());
				case1ExpectedInformationList.add(Information.builder().infoId("00000010").build());
				case1ExpectedInformationList.add(Information.builder().infoId("00000011").build());
				case1ExpectedInformationList.add(Information.builder().infoId("00000012").build());
				case1ExpectedInformationList.add(Information.builder().infoId("00000013").build());
				case1ExpectedInformationList.add(Information.builder().infoId("00000015").build());
				findInformationFixture[0].setCurrentTime(calendar.getTime());
				findInformationFixture[0].expected.addAll(case1ExpectedInformationList);
				
				List<Information> case2ExpectedInformationList = new ArrayList<Information>();
				case2ExpectedInformationList.add(Information.builder().infoId("00000000").build());
				case2ExpectedInformationList.add(Information.builder().infoId("00000001").build());
				case2ExpectedInformationList.add(Information.builder().infoId("00000002").build());
				case2ExpectedInformationList.add(Information.builder().infoId("00000003").build());
				case2ExpectedInformationList.add(Information.builder().infoId("00000004").build());
				case2ExpectedInformationList.add(Information.builder().infoId("00000007").build());
				case2ExpectedInformationList.add(Information.builder().infoId("00000008").build());
				case2ExpectedInformationList.add(Information.builder().infoId("00000010").build());
				case2ExpectedInformationList.add(Information.builder().infoId("00000011").build());
				case2ExpectedInformationList.add(Information.builder().infoId("00000013").build());
				case2ExpectedInformationList.add(Information.builder().infoId("00000015").build());
				findInformationFixture[1].setCurrentTime(calendar.getTime());
				findInformationFixture[1].expected.addAll(case2ExpectedInformationList);
				
				List<Information> case3ExpectedInformationList = new ArrayList<Information>();
				case3ExpectedInformationList.add(Information.builder().infoId("00000000").build());
				case3ExpectedInformationList.add(Information.builder().infoId("00000001").build());
				case3ExpectedInformationList.add(Information.builder().infoId("00000002").build());
				case3ExpectedInformationList.add(Information.builder().infoId("00000003").build());
				case3ExpectedInformationList.add(Information.builder().infoId("00000004").build());
				case3ExpectedInformationList.add(Information.builder().infoId("00000008").build());
				case3ExpectedInformationList.add(Information.builder().infoId("00000010").build());
				case3ExpectedInformationList.add(Information.builder().infoId("00000012").build());
				case3ExpectedInformationList.add(Information.builder().infoId("00000014").build());
				case3ExpectedInformationList.add(Information.builder().infoId("00000015").build());
				findInformationFixture[2].setCurrentTime(calendar.getTime());
				findInformationFixture[2].expected.addAll(case3ExpectedInformationList);
				
				List<Information> case4ExpectedInformationList = new ArrayList<Information>();
				case4ExpectedInformationList.add(Information.builder().infoId("00000000").build());
				case4ExpectedInformationList.add(Information.builder().infoId("00000002").build());
				case4ExpectedInformationList.add(Information.builder().infoId("00000004").build());
				case4ExpectedInformationList.add(Information.builder().infoId("00000005").build());
				case4ExpectedInformationList.add(Information.builder().infoId("00000006").build());
				case4ExpectedInformationList.add(Information.builder().infoId("00000008").build());
				findInformationFixture[3].setCurrentTime(calendar.getTime());
				findInformationFixture[3].expected.addAll(case4ExpectedInformationList);

			}
			
			@DataPoints
			public static FindInformationByUserIdAndReleaseDateLessThanFixture[] findInformationFixture = {
					FindInformationByUserIdAndReleaseDateLessThanFixture.builder()
							.userId("00000000")
							.expected((List<Information>)new ArrayList<Information>())
							.build(),
					FindInformationByUserIdAndReleaseDateLessThanFixture.builder()
							.userId("00000001")
							.expected((List<Information>)new ArrayList<Information>())
							.build(),
					FindInformationByUserIdAndReleaseDateLessThanFixture.builder()
							.userId("00000002")
							.expected((List<Information>)new ArrayList<Information>())
							.build(),
					FindInformationByUserIdAndReleaseDateLessThanFixture.builder()
							.userId("00000003")
							.expected((List<Information>)new ArrayList<Information>())
							.build()
			};
			
			@Theory
			@Category(TestsWithDatabaseAccess.class)
			public void normalTestCase_findInformationByUserIdAndReleaseDateLessThan(
					FindInformationByUserIdAndReleaseDateLessThanFixture fixture){
				
				List<Information> InformationList = InformationRepository
						.findUserByUserIdAndReleaseDateLessThan(fixture.userId, fixture.currentTime);
				
				for(int i=0 ; i < InformationList.size() ; i++){
					assertThat(fixture.toString(), InformationList.get(i).getInfoId(),
							is(fixture.expected.get(i).getInfoId()));
				}
			}

			@AllArgsConstructor
			@Builder
			@Data
			public static class FindInformationByUserIdAndReleaseDateLessThanFixture{
				String userId;
				Date currentTime;
				List<Information> expected = new ArrayList<Information>();
	            @Override
	            public String toString(){
	                return String.format("when UserId = %s, Date = %s, expceted = %s ", 
	                		userId, currentTime.toString(), expected);
	             }
			}
		}
	}
	
}
