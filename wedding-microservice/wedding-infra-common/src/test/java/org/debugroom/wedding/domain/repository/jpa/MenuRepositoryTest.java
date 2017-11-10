package org.debugroom.wedding.domain.repository.jpa;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.util.List;
import java.util.ArrayList;

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

import org.debugroom.framework.test.junit.category.TestsWithDatabaseAccess;
import org.debugroom.framework.test.junit.category.UnitTests;
import org.debugroom.wedding.config.TestConfig;
import org.debugroom.wedding.domain.entity.Menu;

@RunWith(Enclosed.class)
public class MenuRepositoryTest {

	@Category(UnitTests.class)
	@RunWith(Enclosed.class)
	public static class UnitTest {
		
		@RunWith(Theories.class)
		@ContextConfiguration(classes = TestConfig.class)
		@ActiveProfiles(profiles = {"jpa", "LocalDev"})
		public static class WhiteBoxTest{
			
			@Inject
			MenuRepository menuRepository;
			
			@Before
			public void setUp() throws Exception{
				new TestContextManager(getClass()).prepareTestInstance(this);

				List<Menu> case1ExpectedMenuList = new ArrayList<Menu>();
				case1ExpectedMenuList.add(Menu.builder().menuId("1010").build());
				findMenuFixture[0].expected.addAll(case1ExpectedMenuList);

				List<Menu> case2ExpectedMenuList = new ArrayList<Menu>();
				case2ExpectedMenuList.add(Menu.builder().menuId("1000").build());
				case2ExpectedMenuList.add(Menu.builder().menuId("1020").build());
				case2ExpectedMenuList.add(Menu.builder().menuId("2000").build());
				case2ExpectedMenuList.add(Menu.builder().menuId("3000").build());
				case2ExpectedMenuList.add(Menu.builder().menuId("8000").build());
				findMenuFixture[1].expected.addAll(case2ExpectedMenuList);

				List<Menu> case3ExpectedMenuList = new ArrayList<Menu>();
				case3ExpectedMenuList.add(Menu.builder().menuId("1000").build());
				case3ExpectedMenuList.add(Menu.builder().menuId("1020").build());
				case3ExpectedMenuList.add(Menu.builder().menuId("2000").build());
				case3ExpectedMenuList.add(Menu.builder().menuId("3000").build());
				case3ExpectedMenuList.add(Menu.builder().menuId("8000").build());
				case3ExpectedMenuList.add(Menu.builder().menuId("9000").build());
				case3ExpectedMenuList.add(Menu.builder().menuId("9010").build());
				case3ExpectedMenuList.add(Menu.builder().menuId("9020").build());
				case3ExpectedMenuList.add(Menu.builder().menuId("9030").build());
				findMenuFixture[2].expected.addAll(case3ExpectedMenuList);

			}
			
			@DataPoints
			public static FindMenuByAuthorityLevelLessThanFixture[] findMenuFixture = {
				new FindMenuByAuthorityLevelLessThanFixture
						.FindMenuByAuthorityLevelLessThanFixtureBuilder()
						.authorityLevel(1)
						.expected((List<Menu>)new ArrayList<Menu>())
						.build(),
				new FindMenuByAuthorityLevelLessThanFixture
						.FindMenuByAuthorityLevelLessThanFixtureBuilder()
						.authorityLevel(2)
						.expected((List<Menu>)new ArrayList<Menu>())
						.build(),
				new FindMenuByAuthorityLevelLessThanFixture
						.FindMenuByAuthorityLevelLessThanFixtureBuilder()
						.authorityLevel(9)
						.expected((List<Menu>)new ArrayList<Menu>())
						.build(),
			};
			
			@Theory
			@Category(TestsWithDatabaseAccess.class)
			public void normalTestCase1_findByAuthorityLevelLessThan(FindMenuByAuthorityLevelLessThanFixture fixture){

				List<Menu> menuList = menuRepository.findByAuthorityLevelLessThanOrderByMenuIdAsc(fixture.authorityLevel);
				
				for(int i = 0 ; i < menuList.size() ; i++){
					assertThat(fixture.toString(), menuList.get(i).getMenuId(),
							is(fixture.expected.get(i).getMenuId()));
				}
				
			}
			@AllArgsConstructor
			@Builder
			public static class FindMenuByAuthorityLevelLessThanFixture{
				int authorityLevel;
				List<Menu> expected = new ArrayList<Menu>();
	            @Override
	            public String toString(){
	                return String.format("when exception = %s, expceted = %s ", authorityLevel, expected);
	             }
			}
		}
	}
	
}
