package org.debugroom.wedding.app.batch.gallery.model;

import lombok.Builder;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class Photo implements Serializable{

	private static final long serialVersionUID = 6127593861018578967L;

	public Photo(){
	}

	private String photoId;

}
