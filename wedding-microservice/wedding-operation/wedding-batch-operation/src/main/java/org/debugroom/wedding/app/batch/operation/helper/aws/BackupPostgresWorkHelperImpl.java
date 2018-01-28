package org.debugroom.wedding.app.batch.operation.helper.aws;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.debugroom.framework.common.exception.SystemException;
import org.debugroom.wedding.app.batch.operation.helper.BackupWorkHelper;
import org.debugroom.wedding.domain.service.common.DateUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.WritableResource;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.stereotype.Component;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

@Component("backupPostgresWorkHelper")
public class BackupPostgresWorkHelperImpl extends AbstractBackupWorkHelper{

	private static final String S3_BUCKET_PREFIX = "s3://";
	
	@Value("${bucket.name}")
	private String bucketName;
	
	@Value("${operation.backup.root.directory}")
	private String operationBackupRootDirectory;
	
	@Value("${operation.backup.directory.postgres}")
	private String operationBackupPostgresDirectory;
	
	@Inject
	private ResourcePatternResolver resourcePatternResolver;
	
	@Inject
	private AmazonS3 amazonS3;

	@Inject
	private ResourceLoader resourceLoader;
	
	@Override
	public String createBackupDirectory() {
		String s3UploadRootPath = new StringBuilder()
				.append(S3_BUCKET_PREFIX)
				.append(bucketName)
				.append("/")
				.toString();
		String backupDirectory = new StringBuilder()
				.append(operationBackupRootDirectory)
				.append("/")
				.append(operationBackupPostgresDirectory)
				.append("/")
				.append(StringUtils.substringBefore(DateUtil.getCurrentDate().toString(), " "))
				.toString();
		List<Resource> resources;
		ObjectMetadata metadata = new ObjectMetadata();
		metadata.setContentLength(0);
		InputStream emptyContent = new ByteArrayInputStream(new byte[0]);
		try {
			resources = Arrays.asList(resourcePatternResolver.getResources(
					new StringBuilder()
					.append(s3UploadRootPath)
					.append(backupDirectory)
					.append("/**")
					.toString()));
			if(resources.size() == 0){
				PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName,
						new StringBuilder().append(backupDirectory).append("/").toString(),
						emptyContent, metadata);
				amazonS3.putObject(putObjectRequest);
			}
		} catch (IOException e) {
			new SystemException("operation.backup.helper.error.0001", backupDirectory, e);
		} finally{
			try {
				emptyContent.close();
			} catch (IOException e) {
			new SystemException("operation.backup.helper.error.0002", backupDirectory, e);
			}
		}
		return backupDirectory;
	}

}
