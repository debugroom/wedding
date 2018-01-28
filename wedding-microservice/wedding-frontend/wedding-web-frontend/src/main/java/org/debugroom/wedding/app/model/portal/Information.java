package org.debugroom.wedding.app.model.portal;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class Information implements Serializable{

	private static final long serialVersionUID = -6124594840279093380L;

	public Information(){
	}
	
	@NotNull
	@Size(min=8, max=8)
	@Pattern(regexp = "[0-9]*")
	private String infoId;
	private String infoPagePath;
	private Date lastUpdatedDate;
	private Date registratedDate;
	private Date releaseDate;
	private String title;
	private Integer ver;
	private String infoRootPath;

}
