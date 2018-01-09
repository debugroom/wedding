package org.debugroom.wedding.app.batch.operation.helper;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public abstract class AbstractBackupWorkHelper implements BackupWorkHelper{

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
