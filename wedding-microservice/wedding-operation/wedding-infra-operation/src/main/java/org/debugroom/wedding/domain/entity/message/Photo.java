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
public class Photo implements Serializable{

	private static final long serialVersionUID = -5962928508291505271L;

	public Photo(){
	}
	
	@Column("media_type")
	private MediaType medeiaType;
	@Column("file_path")
	private String filePath;

}
