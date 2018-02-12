package org.debugroom.wedding.app.web.helper.aws;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.inject.Inject;

import org.apache.commons.codec.binary.Hex;
import org.apache.tomcat.util.codec.binary.Base64;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.core.region.RegionProvider;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.stereotype.Component;

import com.amazonaws.auth.SystemPropertiesCredentialsProvider;
import com.amazonaws.auth.policy.Policy;
import com.amazonaws.auth.policy.Resource;
import com.amazonaws.auth.policy.Statement;
import com.amazonaws.auth.policy.actions.S3Actions;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.identitymanagement.AmazonIdentityManagementClientBuilder;
import com.amazonaws.services.identitymanagement.model.GetRoleRequest;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.securitytoken.AWSSecurityTokenServiceClientBuilder;
import com.amazonaws.services.securitytoken.model.AssumeRoleRequest;
import com.amazonaws.services.securitytoken.model.Credentials;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.debugroom.framework.common.exception.SystemException;
import org.debugroom.wedding.app.model.aws.gallery.DirectUploadAuthorization;
import org.debugroom.wedding.app.model.aws.gallery.PostPolicy;

@Component
public class GalleryContentsUploadHelper implements InitializingBean{

	private static final String S3_BUCKET_PREFIX = "s3://";
	private static final String RESOURCE_ARN_PREFIX = "arn:aws:s3:::";
	private static final String ALGORITHM = "HmacSHA256";
	private static final int STS_MIN_DURATION_MINUTES = 15;
	private static final String SERVICE_NAME = "s3";

	@Value("${gallery.root.directory}")
	private String galleryRootDirectory;
	@Value("${gallery.image.original.directory}")
	private String galleryImageOriginalDirectory;
	@Value("${gallery.movie.original.directory}")
	private String galleryMovieOriginalDirectory;
	@Value("${gallery.aws.upload.role.name}")
	private String roleName;
	@Value("${gallery.aws.upload.role.session.name}")
	private String roleSessionName;
	@Value("${bucket.name}")
	private String bucketName;
	@Value("${cloud.aws.credentials.accessKey}")
	private String accessKey;
	@Value("${cloud.aws.credentials.secretKey}")
	private String secretKey;
    @Value("${cloud.aws.region.static}")
    private String region;
	@Value("${gallery.aws.upload.acl}")
	private String ACCESS_CONTROL_LEVEL;
	// PostPolicyの有効時間(秒)
	@Value("${gallery.aws.upload.durationseconds:30}")
	private int durationSeconds; 
	// アップロードサイズ上限(バイト)
	@Value("${gallery.aws.upload.limitBytes}")
	private String fileSizeLimit;
	
	private String roleArn;
	
	@Inject
	ObjectMapper objectMapper;
	
	@Inject
	RegionProvider regionProvider;
	
	@Inject
	AmazonS3 amazonS3;
	
	@Inject
	ResourcePatternResolver resourcePatternResolver;
	
	public String createImageDirectUploadDirectories(String userId){
		String s3UploadBucket = new StringBuilder()
				.append(S3_BUCKET_PREFIX)
				.append(bucketName)
				.append("/")
				.toString();
		String uploadRootDirectory = new StringBuilder()
				.append(galleryRootDirectory).append("/")
				.append(userId)
				.toString();
		String uploadDirectory = new StringBuilder()
				.append(uploadRootDirectory)
				.append("/")
				.append(galleryImageOriginalDirectory)
				.toString();
		if(!existsDirectory(new StringBuilder()
				.append(s3UploadBucket).append(uploadRootDirectory).toString())){
			createDirectory(uploadRootDirectory);
			createDirectory(uploadDirectory);
		}
		return uploadDirectory;
	}
	
	public String createMovieDirectUploadDirectories(String userId){
		String s3UploadBucket = new StringBuilder()
				.append(S3_BUCKET_PREFIX)
				.append(bucketName)
				.append("/")
				.toString();
		String uploadRootDirectory = new StringBuilder()
				.append(galleryRootDirectory).append("/")
				.append(userId)
				.toString();
		String uploadDirectory = new StringBuilder()
				.append(uploadRootDirectory)
				.append("/")
				.append(galleryMovieOriginalDirectory)
				.toString();
		if(!existsDirectory(new StringBuilder()
				.append(s3UploadBucket).append(uploadRootDirectory).toString())){
			createDirectory(uploadRootDirectory);
			createDirectory(uploadDirectory);
		}
		return uploadDirectory;
	}
	
	private void createDirectory(String directoryPath){
		ObjectMetadata metadata = new ObjectMetadata();
		InputStream emptyContent = new ByteArrayInputStream(new byte[0]);
		try{
			PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName,
				new StringBuilder().append(directoryPath).append("/").toString(),
				emptyContent, metadata);
			amazonS3.putObject(putObjectRequest);
		}finally{
			try {
				emptyContent.close();
			} catch (IOException e) {
				new SystemException("common.error.0001", directoryPath, e);
			}
		}
	
	}
	
	private boolean existsDirectory(String directoryPath){
		try {
			List<org.springframework.core.io.Resource> resources = 
				Arrays.asList(resourcePatternResolver.getResources(
					new StringBuilder().append(directoryPath).append("/**").toString()));
			if(resources.size() == 0){
				return false;
			}
		} catch (IOException e) {
			new SystemException("common.error.0001", directoryPath, e);
		}
		return true;
	}

    /**
     * ブラウザからPOSTメソッドでダイレクトアップロードするために必要な認証情報を作成するメソッド
     * @param directory
     * @param fileName
     * @return
     */
	public DirectUploadAuthorization createDirectUploadAuthorization(String directory){

		String objectKey = new StringBuilder().append(directory).append("/").toString();
		Credentials credentials = getTemporaryCredentials(objectKey);
//		String regionName = regionProvider.getRegion().getName();
		String regionName = region;
		DateTime nowUTC = new DateTime(DateTimeZone.UTC);
		String date = nowUTC.toString("yyyyMMdd");
		String credentialString = createCredentialString(
				credentials.getAccessKeyId(), date, regionName, SERVICE_NAME);
		String securityToken = credentials.getSessionToken();
		String uploadUrl = createUploadUrl(regionName, directory);
		String algorithm = "AWS4-HMAC-SHA256";
		String iso8601dateTime = nowUTC.toString("yyyyMMdd'T'HHmmss'Z'");
		
		/*
		 * PostPolicyの作成。ブラウザからPOSTメソッドで非公開バケットへアップロードを行う場合、
		 * リクエストにPOSTポリシーを含める必要がある。POSTポリシーは一時認証情報に加え、
		 * アップロードファイルの上限やアップロードファイルのオブジェクトキーパターン、
		 * メタデータ(オブジェクトに付加するデータ)の情報を追加する。POSTポリシーに含まれていない
		 * 情報がアップロードリクエストに含まれていた場合、アップロードに失敗するので注意。
		 * 書式はhttps://docs.aws.amazon.com/AmazonS3/latest/API/sigv4-post-example.htmlを参照
		 */
		PostPolicy postPolicy = PostPolicy.builder()
				.expiration(nowUTC.plusSeconds(durationSeconds).toString()) //アップロード有効期限
				.conditions(new String[][]{
					{"eq", "$bucket", bucketName},                         // バケット名が完全一致
					{"starts-with", "$key", objectKey},             // オブジェクトキー名が前方一致
					{"eq", "$acl", ACCESS_CONTROL_LEVEL},                  // ACLが完全一致
//					{"eq", "$x-amz-meta-filename", fileName},       // ファイル名が完全一致
					{"eq", "$x-amz-credential", credentialString},         // 認証情報が完全一致
					{"eq", "$x-amz-security-token", securityToken},        // セキュリティトークンが完全一致
					{"eq", "$x-amz-algorithm", algorithm},                 // アルゴリズムが完全一致
					{"eq", "$x-amz-date", iso8601dateTime},                // 日付書式が完全一致
					{"content-length-range", "0", fileSizeLimit},   // ファイル上限サイズを指定
				})
				.build();
		
		String policyDocument = null;
		try {
			policyDocument = objectMapper.writeValueAsString(postPolicy);
		} catch (JsonProcessingException e) {
			new SystemException("galleryContentsUploadHelper.error.0003", postPolicy.toString(), e);
		}
		// POSTポリシーはBase64でエンコードする必要がある。
		String base64Policy = Base64.encodeBase64String(policyDocument.getBytes(
				StandardCharsets.UTF_8));
		
		// シークレットアクセスキーで署名を作成する。
		byte[] signingKey = getSignatureKey(credentials.getSecretAccessKey(), 
				date, regionName, SERVICE_NAME);
		
		// signatureをpolicyとセットで渡す必要があるため、HMAC SHA-1を使ってpolicyをSecret Keyで署名する。
		String signatureForPolicy = Hex.encodeHexString(calculateHmacSHA256(
				base64Policy, signingKey));
		
		return DirectUploadAuthorization.builder()
				.uploadUrl(uploadUrl)         // POSTメソッドでアクセスするアップロード先のURL
				.acl(ACCESS_CONTROL_LEVEL)    // アップロードしたファイルの公開範囲
				.date(iso8601dateTime)        // 日時情報(UTCTimezone ISO8601:YYYYMMDD'T'HHMMSS'Z')
				.objectKey(objectKey)         // アップロードファイルのオブジェクトキー
				.securityToken(securityToken) // 一時的認証情報のセキュリティトークン
				.algorithm(algorithm)         // "AWS4-HMAC-SHA256"
				.credential(credentialString) // 一時的認証情報やリージョンを含む文字列
				.signature(signatureForPolicy)// POSTポリシーに対する署名
				.policy(base64Policy)         // POSTポリシードキュメント(Base64エンコードが必要)
//				.rawFileName(fileName)        // アップロードファイル名
				.fileSizeLimit(fileSizeLimit) // アップロードファイルサイズ上限
				.build();
	}

	private Credentials getTemporaryCredentials(String objectKey){
		//アクセスするリソース(アップロード先のS3のディレクトリ)のARNを作成
		String resourceArn = new StringBuilder()
				.append(RESOURCE_ARN_PREFIX)
				.append(bucketName)
				.append("/")
				.append(objectKey)
				.append("*")
				.toString();
		
		// 取得する一時的セキュリティ認証情報に付与するIAMポリシーを設定
		Statement statement = new Statement(Statement.Effect.Allow)
				.withActions(S3Actions.PutObject)
				.withResources(new Resource(resourceArn));
		String iamPolicy = new Policy().withStatements(statement).toJson();
		
		// 要求するAssumeRoleRequestリクエストを作成
		AssumeRoleRequest assumeRoleRequest = new AssumeRoleRequest()
				.withRoleArn(roleArn)
				.withDurationSeconds((int)TimeUnit.MINUTES.toSeconds(STS_MIN_DURATION_MINUTES))
				.withRoleSessionName(roleSessionName)
				.withPolicy(iamPolicy);
		
		// AWSSecurityTokenServiceClientからAssumeRoleRequestを発行し、一時的にセキュリティ認証情報を取得
//		return AWSSecurityTokenServiceClientBuilder.defaultClient()
//				.assumeRole(assumeRoleRequest).getCredentials();
		return AWSSecurityTokenServiceClientBuilder.standard()
				.withCredentials(new SystemPropertiesCredentialsProvider())
				.withRegion(Regions.fromName(region)).build()
				.assumeRole(assumeRoleRequest).getCredentials();
	}
	
	/**
	 * シークレットキーから署名を作成するメソッド
	 *  See https://docs.aws.amazon.com/ja_jp/general/latest/gr/signature-v4-examples.html#signature-v4-examples-java
	 * @param key
	 * @param dateStamp
	 * @param region
	 * @param serviceName
	 * @return
	 */
	private byte[] getSignatureKey(String key, String dateStamp, String region,
			String serviceName){
		byte[] kSecret = new StringBuilder().append("AWS4").append(key).toString()
				.getBytes(StandardCharsets.UTF_8);
		byte[] kDate = calculateHmacSHA256(dateStamp, kSecret);
		byte[] kRegion = calculateHmacSHA256(region, kDate);
		byte[] kService = calculateHmacSHA256(serviceName, kRegion);
		byte[] kSigning = calculateHmacSHA256("aws4_request", kService);
		return kSigning;
	}
	
	/**
	 * 署名キーを使って、指定したアルゴリズム(HMAC)でハッシュ操作するメソッド
	 * @param stringToSign
	 * @param signingKey
	 * @return
	 */
	private byte[] calculateHmacSHA256(String stringToSign, byte[] signingKey){
		Mac mac = null;
		try {
			mac = Mac.getInstance(ALGORITHM);
		} catch (NoSuchAlgorithmException e) {
			new SystemException("galleryContentsUploadHelper.error.0001", e);
		}
		try {
			mac.init(new SecretKeySpec(signingKey, ALGORITHM));
		} catch (InvalidKeyException e) {
			new SystemException("galleryContentsUploadHelper.error.0002", e);
		}
		return mac.doFinal(stringToSign.getBytes(StandardCharsets.UTF_8));
	}
	
	/**
	 * POSTリクエストで、X-Amz-Credentialに設定する文字列を作成する
	 * 書式はhttps://docs.aws.amazon.com/AmazonS3/latest/API/sigv4-query-string-auth.html
	 * @param accessKey
	 * @param date
	 * @param regionName
	 * @param serviceName
	 * @return
	 */
	private String createCredentialString(String accessKey, String date, 
			String regionName, String serviceName){
		return new StringBuilder()
				.append(accessKey).append("/")
				.append(date).append("/")
				.append(regionName).append("/")
				.append(serviceName).append("/")
				.append("aws4_request")
				.toString();
	}
	
	/**
	 * アップロード先のURLを作成する
	 * @param regionName
	 * @return
	 */
	private String createUploadUrl(String regionName, String directory){
		return new StringBuilder()
				.append("https://")
				.append(bucketName)
				.append(".")
				.append("s3-")
				.append(regionName)
				.append(".amazonaws.com")
				.append("/")
				.toString();
	}

    /**
     * プロパティセットされたroleNameからroleArnを作成するメソッド。
     * AmazonIdentityManagementClientBuilderにて、リージョン、アクセスキー等を指定して生成した
     * Clientからロール情報を取得する。アクセスキーはSpring Cloudを利用しており、AP内では、
     * 既にcloud.aws.credential.accessKeyとしてプロパティ指定しているため、環境変数の重複設定を避ける目的で
     * 再度プロパティ値をJavaのシステムプロパティへ一旦設定する形で取得する。
     * See: https://docs.aws.amazon.com/ja_jp/sdk-for-java/v1/developer-guide/credentials.html
     * また、ROLE ARNを取得する場合、AWSコンソール等から、対象のユーザにiam:GetRoleアクセス権限与しておく必要がある。
     * See: https://docs.aws.amazon.com/ja_jp/IAM/latest/UserGuide/id_roles_use_passrole.html
     * @throws Exception
     */
	@Override
	public void afterPropertiesSet() throws Exception {
		System.setProperty("aws.accessKeyId", accessKey);
		System.setProperty("aws.secretKey", secretKey);
		GetRoleRequest getRoleRequest = new GetRoleRequest();
		getRoleRequest.setRoleName(roleName);
		/*
		roleArn = AmazonIdentityManagementClientBuilder.defaultClient()
				.getRole(getRoleRequest).getRole().getArn();
		 */
		roleArn = AmazonIdentityManagementClientBuilder.standard()
				.withCredentials(new SystemPropertiesCredentialsProvider())
				.withRegion(Regions.fromName(region)).build()
				.getRole(getRoleRequest).getRole().getArn();
	}
	
	
	

}
