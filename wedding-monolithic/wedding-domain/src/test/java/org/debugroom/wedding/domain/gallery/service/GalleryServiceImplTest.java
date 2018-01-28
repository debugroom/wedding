package org.debugroom.wedding.domain.gallery.service;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

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
import org.debugroom.wedding.domain.gallery.model.Media;
import org.debugroom.wedding.domain.gallery.model.MediaType;

import lombok.AllArgsConstructor;
import lombok.Builder;

@RunWith(Enclosed.class)
public class GalleryServiceImplTest {

	@Category(UnitTests.class)
	@RunWith(Enclosed.class)
	public static class UnitTest{
		
		@RunWith(Theories.class)
		@ContextConfiguration("classpath:META-INF/spring/wedding-test.xml")
		public static class createDownloadZipTest{
			
			@Inject
			GalleryService galleryService;
			
			static List<Media> mediaList1 = null;
			static List<Media> mediaList2 = null;
			
			static{
				mediaList1 = new ArrayList<Media>();
				mediaList1.add(Media.builder()
								.mediaId("0000000000")
								.mediaType(MediaType.PHOTOGRAPH)
								.build());
				mediaList1.add(Media.builder()
								.mediaId("0000000001")
								.mediaType(MediaType.MOVIE)
								.build());
				mediaList1.add(Media.builder()
								.mediaId("0000000002")
								.mediaType(MediaType.PHOTOGRAPH)
								.build());
				mediaList1.add(Media.builder()
								.mediaId("0000000003")
								.mediaType(MediaType.MOVIE)
								.build());
				mediaList2 = new ArrayList<Media>();
				mediaList2.add(Media.builder()
						.mediaId("0000000000")
						.mediaType(MediaType.PHOTOGRAPH)
						.build());
				mediaList2.add(Media.builder()
						.mediaId("0000000001")
						.mediaType(MediaType.MOVIE)
						.build());
				mediaList2.add(Media.builder()
						.mediaId("0000000002")
						.mediaType(MediaType.PHOTOGRAPH)
						.build());
				mediaList2.add(Media.builder()
						.mediaId("0000000003")
						.mediaType(MediaType.MOVIE)
						.build());				
			}

			@Before
			public void setUp() throws Exception{
				new TestContextManager(getClass()).prepareTestInstance(this);
			}
			
			@DataPoints
			public static Fixture[] fixtures = {
					Fixture.builder()
							.mediaList(mediaList1)
							.build(),
					Fixture.builder()
							.mediaList(mediaList2)
							.build()
			};
			
			@Theory
			public void normalTestCase1(Fixture fixture){
				galleryService.createDownloadZipFile(fixture.mediaList);
			}

			@AllArgsConstructor
			@Builder
			public static class Fixture{
				List<Media> mediaList;
			}
		}
	}
}
