package org.debugroom.wedding.common.external.api.impl.json;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

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
import org.debugroom.framework.test.junit.category.TestsWithExternalAccess;
import org.debugroom.framework.test.junit.category.UnitTests;
import org.debugroom.wedding.common.external.model.Address;

import lombok.AllArgsConstructor;
import lombok.Builder;

@RunWith(Enclosed.class)
public class AddressSearchImplTest {

	@Category(UnitTests.class)
	@RunWith(Enclosed.class)
	public static class UnitTest{
		
		@RunWith(Theories.class)
		@ContextConfiguration("classpath:META-INF/spring/wedding-test.xml")
		public static class WhiteBoxTest{
			
			@Inject
			@Named("addressSearch")
			AddressSearchImpl addressSearch;
			
			@Before
			public void setUp() throws Exception{
				new TestContextManager(getClass()).prepareTestInstance(this);
			}
			
			@DataPoints
			public static GetAddressFixture[] getAddressFixture = {
					new GetAddressFixture.GetAddressFixtureBuilder()
						.zipCode("101-0032")
						.expected(Address.builder()
								.fullAddress("東京都千代田区岩本町")
								.build())
						.build()
			};
			
			@Theory
			@Category(TestsWithExternalAccess.class)
			public void normalTestCase1_getAddress(GetAddressFixture fixture){
				
				Address address = addressSearch.getAddress(fixture.zipCode);
				
				assertThat(address.getFullAddress(), is(fixture.expected.getFullAddress()));

			}
			
			@AllArgsConstructor
			@Builder
			public static class GetAddressFixture{
				String zipCode;
				Address expected;
			}

		}
	}
}
