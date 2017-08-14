package org.debugroom.wedding.app.batch.gallery.model;

import lombok.Builder;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class Movie implements Serializable{

	private static final long serialVersionUID = 1154434216148369606L;

	public Movie(){
	}

	private String movieId;
	
}
