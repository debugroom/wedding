package org.debugroom.wedding.domain.entity.message;

import java.io.Serializable;

import org.springframework.data.cassandra.mapping.Column;
import org.springframework.data.cassandra.mapping.UserDefinedType;

import org.debugroom.framework.common.web.MediaType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
@UserDefinedType
public class Movie implements Serializable{

	private static final long serialVersionUID = -6119498222013416524L;

	public Movie(){
	}
	
	@Column("media_type")
	private MediaType medeiaType;
	@Column("file_path")
	private String filePath;

}
