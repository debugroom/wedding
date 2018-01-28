package org.debugroom.wedding.app.batch.operation.helper.aws;

import java.io.IOException;
import java.io.OutputStream;

import javax.inject.Inject;

import org.apache.commons.io.IOUtils;
import org.debugroom.framework.common.exception.SystemException;
import org.debugroom.wedding.app.batch.operation.helper.BackupWorkHelper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.WritableResource;

public abstract class AbstractBackupWorkHelper implements BackupWorkHelper{

	private static final String S3_BUCKET_PREFIX = "s3://";
	
	@Value("${bucket.name}")
	private String bucketName;
	
	@Inject
	private ResourceLoader resourceLoader;
	
	@Override
	public void save(String path, String fileName, String data) throws IOException {
		String s3UploadRootPath = new StringBuilder()
				.append(S3_BUCKET_PREFIX)
				.append(bucketName)
				.append("/")
				.toString();
		String backupDirectory = new StringBuilder()
				.append(path)
				.append("/")
				.toString();
		WritableResource writableResource = (WritableResource)
				resourceLoader.getResource(new StringBuilder()
						.append(s3UploadRootPath)
						.append(backupDirectory)
						.append(fileName)
						.toString());
		try (OutputStream outputStream = writableResource.getOutputStream()){
			IOUtils.write(data, outputStream, "UTF-8");
		} catch(IOException e){
			throw new SystemException("operation.backup.helper.error.0003", fileName, e);
		}
		
	}
	
}
