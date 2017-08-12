package org.debugroom.wedding.app.batch.gallery.partitioner;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import org.apache.commons.lang3.StringUtils;
import org.debugroom.wedding.domain.entity.gallery.Photo;
import org.debugroom.wedding.domain.repository.jpa.gallery.PhotoRepository;

@Data
@Builder
@AllArgsConstructor
@Component
public class PhotoPartitioner implements Partitioner{

	public PhotoPartitioner(){
	}

	private String downloadPhotoListFileName;
	
	@Override
	public Map<String, ExecutionContext> partition(int gridSize) {

		Map<String, ExecutionContext> executionContextMap = new HashMap<>();

		try {
			File file = new File(StringUtils.substringAfter(downloadPhotoListFileName, ":"));
			BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
			String string;
			int index = 0;
			while((string = bufferedReader.readLine()) != null){
				ExecutionContext executionContext = new ExecutionContext();
				executionContext.putString("photoId", string);
				executionContextMap.put("partition" + index, executionContext);
				index++;
			}
			bufferedReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return executionContextMap;
	}

}
