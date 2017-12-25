package org.debugroom.wedding.app.batch.operation.helper;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;

import javax.inject.Inject;

import org.springframework.stereotype.Component;
import org.apache.commons.lang3.StringUtils;
import org.debugroom.wedding.app.batch.operation.OperationBatchProperties;
import org.debugroom.wedding.domain.service.common.DateUtil;

@Component("backupWorkHelper")
public class BackupWorkHelperImpl implements BackupWorkHelper{

	@Inject
	OperationBatchProperties operationBatchProperties;
	
	@Override
	public String createBackupDirectory() {
		String path = new StringBuilder()
				.append(operationBatchProperties.getOperationBackupRootDirectory())
				.append(java.io.File.separator)
				.append(operationBatchProperties.getOperationBackupDirectory())
				.append(java.io.File.separator)
				.append(StringUtils.substringBefore(DateUtil.getCurrentDate().toString(), " "))
				.toString();
		File newDirectory = new File(path);
		return newDirectory.mkdirs() ? path : path;
	}

	@Override
	public void save(String path, String fileName, String data) throws IOException {
		File backupUserFile = new File(new StringBuilder()
				.append(path)
				.append(java.io.File.separator)
				.append(fileName)
				.toString());
		try (BufferedWriter bufferedWriter = new BufferedWriter(
				new FileWriter(backupUserFile))){
			bufferedWriter.write(data);
			bufferedWriter.flush();
		}
	}

}
