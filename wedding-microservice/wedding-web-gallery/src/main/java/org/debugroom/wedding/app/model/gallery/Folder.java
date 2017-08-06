package org.debugroom.wedding.app.model.gallery;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class Folder implements Serializable{

	private static final long serialVersionUID = -1286978922520956717L;

	public Folder(){
	}

	private String folderId;
	private String folderName;
	
}
