package org.debugroom.wedding.app.batch.gallery.step;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.inject.Inject;

import org.springframework.batch.core.StepExecution;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.apache.commons.lang3.StringUtils;
import org.debugroom.wedding.app.batch.gallery.GalleryBatchProperties;
import org.debugroom.wedding.domain.entity.gallery.Photo;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class CreateZipWriter implements ItemWriter<Photo>{

	@Inject
	GalleryBatchProperties galleryBatchProperties;
	
	@Value("#{stepExecution}")
	private StepExecution stepExecution;
	
	@Override
	public void write(List<? extends Photo> photographs) throws Exception {
		// Processorの処理はマルチスレッドで行われるので、最後のスレッドのみWriter処理を実行させる。
		ExecutionContext stepExecutionContext = stepExecution.getExecutionContext();
		ExecutionContext jobExecutionContext = 
				stepExecution.getJobExecution().getExecutionContext();

		BufferedReader bufferedReader = null;
		File downloadDirectoryPath = new File(
				jobExecutionContext.getString("downloadDirectoryPath"));
		try{
			
		File downloadPhotoListFile = new File(
				StringUtils.substringAfter(jobExecutionContext.getString("downloadPhotoListFilename"), ":"));
		bufferedReader = new BufferedReader(new FileReader(downloadPhotoListFile));
		
		String string = null;
		while((string = bufferedReader.readLine()) != null){
			// ファイルがなければ、まだ別スレッドで処理を継続しているので、終了キーを作成して、当スレッドの処理は終了させる。
			if(findFilenameWithId(string, downloadDirectoryPath).length == 0){
				jobExecutionContext.put(new StringBuilder()
						.append("thread-")
						.append(stepExecutionContext.getString("photoId"))
						.toString(), "isCompleted");
				return;
			}
			// ファイルはあるが、終了キーがなければ、後続のスレッド処理があるので当スレッドの処理は終了させる。
			if(!stepExecutionContext.getString("photoId").equals(string)
					&& !jobExecutionContext.containsKey(new StringBuilder()
					.append("thread-")
					.append(string)
					.toString())){
				jobExecutionContext.put(new StringBuilder()
						.append("thread-")
						.append(stepExecutionContext.getString("photoId"))
						.toString(), "isCompleted");
				return;
			}
					
		}

		if(downloadPhotoListFile.exists()){
			downloadPhotoListFile.delete();
		}
		
		} catch(IOException e){
			e.printStackTrace();
		} finally{
			bufferedReader.close();
		}
		
		InputStream inputStream = null;
		ZipOutputStream zipOutputStream = null;
		byte[] buffer = new byte[1024];
		
		try{
			File zipFilePath = new File(new StringBuilder()
					.append(downloadDirectoryPath.getAbsolutePath())
					.append(java.io.File.separator)
					.append(galleryBatchProperties.getGalleryDownloadFilename())
					.toString());
			zipOutputStream = new ZipOutputStream(new BufferedOutputStream(
					new FileOutputStream(zipFilePath)));
			for(File file : findFilenameMatchingRegex("photo-[.0-9a-z]+", downloadDirectoryPath)){
				ZipEntry entry = new ZipEntry(file.getName());
				zipOutputStream.putNextEntry(entry);
				inputStream = new BufferedInputStream(new FileInputStream(file));
				int length = 0;
				while((length = inputStream.read(buffer)) != -1){
					zipOutputStream.write(buffer, 0, length);
				}
				file.delete();
			}
			jobExecutionContext.put("downloadFilePath", zipFilePath.getAbsolutePath());

		}catch (IOException e){
		}finally{
			inputStream.close();
			zipOutputStream.close();
		}
		
		log.info(this.getClass().getName() + " : starts. photograpshs :"  + photographs.toString());

	}

	private File[] findFilenameWithId(String id, File directory){
		return findFilenameMatchingRegex(new StringBuilder()
				.append("photo-").append(id).append("[.a-z]+").toString(), directory);
	}
	
	private File[] findFilenameMatchingRegex(String regex, File directory){
		return directory.listFiles(file -> file.getName().matches(regex));
	}
}
