package org.debugroom.wedding.app.batch.operation.helper;

import java.io.File;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import org.debugroom.wedding.app.batch.operation.OperationBatchProperties;
import org.debugroom.wedding.domain.service.common.DateUtil;

@Profile({"local"})
@Component("backupPostgresWorkHelper")
public class BackupPostgresWorkHelperImpl extends AbstractBackupWorkHelper{

	@Inject
	OperationBatchProperties operationBatchProperties;

	@Override
	public String createBackupDirectory() {
		String path = new StringBuilder()
				.append(operationBatchProperties.getOperationBackupRootDirectory())
				.append(java.io.File.separator)
				.append(operationBatchProperties.getOperationBackupPostgresDirectory())
				.append(java.io.File.separator)
				.append(StringUtils.substringBefore(DateUtil.getCurrentDate().toString(), " "))
				.toString();
		File newDirectory = new File(path);
		return newDirectory.mkdirs() ? path : path;
	}

}
