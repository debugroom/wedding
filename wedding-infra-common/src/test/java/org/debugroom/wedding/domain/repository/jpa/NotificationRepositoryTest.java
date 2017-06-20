package org.debugroom.wedding.domain.repository.jpa;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.util.Date;
import java.util.List;
import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;

import javax.inject.Inject;
import javax.inject.Named;
import javax.sql.DataSource;

import org.dbunit.Assertion;
import org.dbunit.DataSourceDatabaseTester;
import org.dbunit.IDatabaseTester;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.SortedTable;
import org.dbunit.dataset.excel.XlsDataSet;
import org.dbunit.ext.postgresql.PostgresqlDataTypeFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.experimental.runners.Enclosed;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;
import org.springframework.test.context.TestContextManager;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import org.debugroom.framework.test.junit.category.UnitTests;
import org.debugroom.wedding.config.TestConfig;
import org.debugroom.wedding.domain.entity.Notification;
import org.debugroom.wedding.domain.entity.NotificationPK;
import org.debugroom.framework.test.junit.category.TestsWithDatabaseAccess;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

public class NotificationRepositoryTest {

	@Category(UnitTests.class)
	@RunWith(Enclosed.class)
	@ContextConfiguration(classes = TestConfig.class)
	@ActiveProfiles(profiles = {"jpa","dev"})
	public static class UnitTest{
		
		@Inject
		private DataSource dataSource;
			
		private IDatabaseTester databaseTester;
		
		@Before
		public void setUp() throws Exception{
			databaseTester = new DataSourceDatabaseTester(dataSource){
				@Override 
				public IDatabaseConnection getConnection() throws Exception{
					IDatabaseConnection databaseConnection = super.getConnection();
					databaseConnection.getConfig().setProperty(
							DatabaseConfig.PROPERTY_DATATYPE_FACTORY, 
							new PostgresqlDataTypeFactory());
					return databaseConnection;
				}
			};	
		}

		@RunWith(Theories.class)
		@Transactional
		public static class UpdateIsAccessedByInfoIdTest extends UnitTest{
			
			@Inject
			NotificationRepository notificationRepository;
			
			@Before
			public void setUp() throws Exception{
				new TestContextManager(getClass()).prepareTestInstance(this);
				super.setUp();
				super.databaseTester.setDataSet(getDataSet());
				super.databaseTester.onSetup();
				
				List<Notification> case1ExpectedNotificationList = new ArrayList<Notification>();
				case1ExpectedNotificationList.add(Notification.builder()
													.id(NotificationPK.builder().infoId("00000000").userId("00000001").build())
													.isAccessed(false)
													.build());
				case1ExpectedNotificationList.add(Notification.builder()
													.id(NotificationPK.builder().infoId("00000000").userId("00000002").build())
													.isAccessed(false)
													.build());
				case1ExpectedNotificationList.add(Notification.builder()
													.id(NotificationPK.builder().infoId("00000000").userId("00000003").build())
													.isAccessed(false)
													.build());
				case1ExpectedNotificationList.add(Notification.builder()
													.id(NotificationPK.builder().infoId("00000000").userId("00000004").build())
													.isAccessed(false)
													.build());
			}
			
			@DataPoints
			public static UpdateIsAccessedByInfoIdFixture[] fixtures = {
				UpdateIsAccessedByInfoIdFixture.builder()
					.isAccessed(false)
					.infoId("00000000")
					.expected((List<Notification>)new ArrayList<Notification>())
					.build()
			};
			
			@Theory
			@Category(TestsWithDatabaseAccess.class)
			public void normalTestCase_updateIsAccessedByInfoId(
					UpdateIsAccessedByInfoIdFixture fixture){
				
				//TODO javax.persistence.TransactionRequiredException has occuered.
				//     Investigating the reason @Transactional does not work.
				/*
				 * 
				notificationRepository.updateIsAccessedByInfoId(
						fixture.isAccessed, fixture.infoId);
				
				List<Notification> notifications = notificationRepository
						.findByIdInfoIdOrderByIdUserIdAsc(fixture.infoId);

				for(int i=0; i < notifications.size() ; i++){
					assertThat(fixture.toString(), notifications.get(i).getIsAccessed(),
							is(fixture.expected.get(i).getIsAccessed()));
				}
				 */

			}

			protected IDataSet getDataSet() throws Exception{
				URL url = this.getClass().getClassLoader().getResource(
						"org/debugroom/wedding/domain/repository/NotificationRepositoryTest_testcase1_initialData.xls");
				InputStream inputStream = new BufferedInputStream(url.openStream());
				XlsDataSet dataSet = new XlsDataSet(inputStream);
				return dataSet;
			}

			@AllArgsConstructor
			@Builder
			@Data
			public static class UpdateIsAccessedByInfoIdFixture{
				boolean isAccessed;
				String infoId;
				List<Notification> expected = new ArrayList<Notification>();
				@Override
				public String toString(){
					return String.format("when infoId = %s, isAccessed = %s, expected = %s",
							infoId, isAccessed, expected);
				}
			}
		}
	}
	
}
