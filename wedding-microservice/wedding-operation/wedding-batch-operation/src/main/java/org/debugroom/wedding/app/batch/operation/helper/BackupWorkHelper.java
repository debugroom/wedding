package org.debugroom.wedding.app.batch.operation.helper;

import java.io.IOException;

public interface BackupWorkHelper {

	public String createBackupDirectory();
	
	public void save(String path, String fileName, String data) throws IOException;
	
}
