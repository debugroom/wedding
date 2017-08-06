package org.debugroom.wedding.domain.gallery.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;

import org.debugroom.wedding.domain.common.DomainConsts;
import org.debugroom.wedding.domain.common.DomainProperties;
import org.debugroom.wedding.domain.gallery.model.Media;
import org.debugroom.wedding.domain.gallery.model.MediaType;
import org.springframework.stereotype.Service;

@Service("fileService")
public class FileServiceImpl implements FileService{

	@Inject
	DomainProperties domainProperties;
	
	@Override
	public Media createZipFile(List<Media> mediaList, String destPath) {
		
		List<String> mediaFilePathList = new ArrayList<String>();
		for(Media media : mediaList){
			if(null != media.getFilePath()){
				switch(media.getMediaType()){
					case PHOTOGRAPH:
						mediaFilePathList.add(new StringBuilder()
							.append(domainProperties.getGalleryImageRootDirectory())
							.append(java.io.File.separator)
							.append(media.getFilePath())
							.toString());
						break;
					case MOVIE:
						mediaFilePathList.add(new StringBuilder()
							.append(domainProperties.getGalleryMovieRootDirectory())
							.append(java.io.File.separator)
							.append(media.getFilePath())
							.toString());
						break;
					default :
						break;
				}
			}
		}
		String copyMediaCommandPath = new StringBuilder()
								.append(domainProperties.getGalleryDownloadCommandPath())
								.append(java.io.File.separator)
								.append(domainProperties.getGalleryDownloadCommandCopyMedia())
								.toString();

		StringBuilder paramBuilder = new StringBuilder();
		for(String mediaFilePath : mediaFilePathList){
			paramBuilder.append(mediaFilePath)
						.append(DomainConsts.WHITESPACE);
		}
		String param = paramBuilder.toString();

		Media zipFile = Media.builder().mediaType(MediaType.ZIP).build();

		try {
			ProcessBuilder copyMediaProcessBuilder = 
					new ProcessBuilder(domainProperties.getGalleryDownloadCommand(),
					copyMediaCommandPath, param, destPath);
			File executedDir = new File(domainProperties.getGalleryDownloadCommandPath());
			copyMediaProcessBuilder.directory(executedDir);
			Process copyMediaProcess = copyMediaProcessBuilder.start();
/**
 * 
			InputStream is = process.getInputStream();	//標準出力
			printInputStream(is);
			InputStream es = process.getErrorStream();	//標準エラー
			printInputStream(es);
 */
			if(0 == copyMediaProcess.waitFor()){
				String createZipCommandPath = new StringBuilder()
						.append(domainProperties.getGalleryDownloadCommandPath())
						.append(java.io.File.separator)
						.append(domainProperties.getGalleryDownloadCommandCreateZip())
						.toString();
				ProcessBuilder createZipProcessBuilder = 
						new ProcessBuilder(domainProperties.getGalleryDownloadCommand(),
						createZipCommandPath, domainProperties.getGalleryDownloadDirectoryName(),
						destPath);
				createZipProcessBuilder.directory(executedDir);
				Process createZipProcess = createZipProcessBuilder.start();
				
				if(0 == createZipProcess.waitFor()){
					InputStream inputStream = createZipProcess.getInputStream();
					zipFile.setFilePath(getZipFilePath(inputStream));
				}
			}

		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		
		return zipFile;
	}

	private String getZipFilePath(InputStream inputStream) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
		String zipFileName = null;
		try {
			String line = null;
			String regexp = "download-[-0-9]*.zip";
			Pattern pattern = Pattern.compile(regexp);
			Matcher matcher = null;
			for (;;) {
				line = br.readLine();
				if (line == null) break;
				matcher = pattern.matcher(line);
				if(matcher.find()){
					zipFileName = line;
				}
			}
			return zipFileName;
		} finally {
			br.close();
		}
	}
}
