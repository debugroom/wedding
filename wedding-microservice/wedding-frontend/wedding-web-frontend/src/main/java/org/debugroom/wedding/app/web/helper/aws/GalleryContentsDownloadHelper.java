package org.debugroom.wedding.app.web.helper.aws;

import java.net.URL;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.debugroom.wedding.app.model.aws.gallery.Movie;
import org.debugroom.wedding.app.model.aws.gallery.Photo;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.amazonaws.HttpMethod;
import com.amazonaws.auth.STSAssumeRoleSessionCredentialsProvider;
import com.amazonaws.auth.SystemPropertiesCredentialsProvider;
import com.amazonaws.auth.policy.Policy;
import com.amazonaws.auth.policy.Resource;
import com.amazonaws.auth.policy.Statement;
import com.amazonaws.auth.policy.actions.S3Actions;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.identitymanagement.AmazonIdentityManagementClientBuilder;
import com.amazonaws.services.identitymanagement.model.GetRoleRequest;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.ResponseHeaderOverrides;

@Component
public class GalleryContentsDownloadHelper implements InitializingBean{

	private static final String RESOURCE_ARN_PREFIX = "arn:aws:s3:::";
	private static final int STS_MIN_DURATION_MINUTES = 15;

//	@Value("${bucket.name}")
//	private String bucketName;
	@Value("${gallery.bucket.name}")
	private String galleryBucketName;
	@Value("${gallery.root.directory}")
	private String galleryRootDirectory;
	@Value("${cloud.aws.credentials.accessKey}")
	private String accessKey;
	@Value("${cloud.aws.credentials.secretKey}")
	private String secretKey;
    @Value("${cloud.aws.region.static}")
    private String region;
	@Value("${gallery.aws.download.role.name}")
	private String roleName;
	@Value("${gallery.aws.download.role.session.name}")
	private String roleSessionName;
	@Value("${gallery.aws.download.durationseconds}")
	private int durationseconds;
    
	private String roleArn;
	
	public void setPhotoThumbnailPresignedUrls(List<Photo> photographs){
		for(Photo photo : photographs){
			photo.setThumbnailPresignedUrl(getPresignedUrl(photo.getThumbnailFilePath()).toString());
		}
	}
	
	public void setMovieThumbnailPresignedUrls(List<Movie> movies){
		for(Movie movie : movies){
			movie.setThumbnailPresignedUrl(getPresignedUrl(movie.getThumbnailFilePath()).toString());
		}
	}

	public URL getPresignedUrl(String filePath){
		String objectKey = new StringBuilder()
				.append(galleryRootDirectory)
				.append("/")
				.append(filePath)
				.toString();
		AmazonS3 amazonS3 = getS3ClientWithDownloadPolicy(objectKey);
		Date expiration = Date.from(ZonedDateTime.now().plusSeconds(durationseconds).toInstant());
//		return amazonS3.generatePresignedUrl(bucketName, objectKey, expiration);
		return amazonS3.generatePresignedUrl(galleryBucketName, objectKey, expiration);
	}
	
	public URL getDownloadPresignedUrl(String filePath){
		
		String objectKey = new StringBuilder()
				.append(galleryRootDirectory)
				.append("/")
				.append(filePath)
				.toString();
		AmazonS3 amazonS3 = getS3ClientWithDownloadPolicy(objectKey);
		Date expiration = Date.from(ZonedDateTime.now().plusSeconds(durationseconds).toInstant());
		
		ResponseHeaderOverrides responseHeaders = new ResponseHeaderOverrides();
        responseHeaders.withContentDisposition(new StringBuilder()
        		.append("attachment;filename=")
        		.append(StringUtils.substringAfterLast(filePath, "/"))
        		.toString());
        
        GeneratePresignedUrlRequest generatePresignedUrlRequest = 
//        		new GeneratePresignedUrlRequest(bucketName, objectKey, HttpMethod.GET);
        		new GeneratePresignedUrlRequest(galleryBucketName, objectKey, HttpMethod.GET);
        generatePresignedUrlRequest.withExpiration(expiration);
        generatePresignedUrlRequest.withResponseHeaders(responseHeaders);
        
        return amazonS3.generatePresignedUrl(generatePresignedUrlRequest);

	}
	
	private AmazonS3 getS3ClientWithDownloadPolicy(String objectKey){

		//アクセスするリソース(ダウンロードするS3オブジェクト)のARNを作成
		String resourceArn = new StringBuilder()
				.append(RESOURCE_ARN_PREFIX)
//				.append(bucketName)
				.append(galleryBucketName)
				.append("/")
				.append(objectKey)
				.toString();
		
		// 一時的なセキュリティ認証情報を付与するIAMポリシーを設定
		Statement statement = new Statement(Statement.Effect.Allow)
				.withActions(S3Actions.GetObject)
				.withResources(new Resource(resourceArn));
		String iamPolicy = new Policy().withStatements(statement).toJson();
		
		// 作成したポリシーを指定したS3クライアントを作成する。
		return AmazonS3ClientBuilder.standard()
				.withCredentials(new STSAssumeRoleSessionCredentialsProvider
						.Builder(roleArn, roleSessionName)
						.withRoleSessionDurationSeconds(
								(int)TimeUnit.MINUTES.toSeconds(STS_MIN_DURATION_MINUTES))
						.withScopeDownPolicy(iamPolicy)
						.build())
				.build();
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		System.setProperty("aws.accessKeyId", accessKey);
		System.setProperty("aws.secretKey", secretKey);
		GetRoleRequest getRoleRequest = new GetRoleRequest().withRoleName(roleName);
		roleArn = AmazonIdentityManagementClientBuilder.standard()
				.withCredentials(new SystemPropertiesCredentialsProvider())
				.withRegion(Regions.fromName(region)).build()
				.getRole(getRoleRequest).getRole().getArn();
	}

}
