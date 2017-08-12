package org.debugroom.wedding.domain.service.common;

import java.io.File;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service("FileSystemSharedService")
public class FileSystemSharedServiceImpl implements FileSystemSharedService{
	
	@Override
	public String createDirectory(String path, String dirName){
		String absolutePath = new StringBuilder()
								.append(path)
								.append(java.io.File.separator)
								.append(dirName)
								.toString();
		File newDirectory = new File(absolutePath);
		return newDirectory.mkdirs() ? absolutePath : null;
	}

}
