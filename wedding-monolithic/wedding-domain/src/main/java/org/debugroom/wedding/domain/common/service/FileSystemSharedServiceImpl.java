package org.debugroom.wedding.domain.common.service;

import java.io.File;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service("FileSystemSharedService")
public class FileSystemSharedServiceImpl implements FileSystemSharedService{

	@Value("${domain.common.tempdirectory}")
	private String tempRootDirectory;
	
	@Override
	public String createDirectory(String path, String dirName){
		String absolutePath = new StringBuilder()
								.append(tempRootDirectory)
								.append(java.io.File.separator)
								.append(path)
								.append(java.io.File.separator)
								.append(dirName)
								.toString();
		File newDirectory = new File(absolutePath);
		return newDirectory.mkdirs() ? absolutePath : null;
	}

}
