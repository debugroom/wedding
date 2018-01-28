package org.debugroom.wedding.domain.common.service;

import org.debugroom.framework.common.exception.BusinessException;

public interface FileSystemSharedService {

	public String createDirectory(String path, String dirName);
	
}
