package org.debugroom.wedding.app.web.management.infomation;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class Infomation implements Serializable{

	private static final long serialVersionUID = 82349142472284365L;

	public Infomation(){
	}

	@NotNull
	@Size(min=8, max=8)
	@Pattern(regexp = "[0-9]*")
	private String infoId;

	@NotNull
	@Size(max=256)
	private String title;

	@NotNull
	@Size(max=256)
	@Pattern(regexp = "[-¥.¥_¥/a-zA-Z0-9]*")
	private String infoPagePath;
	
	@NotNull
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@Past
	private Date registratedDate;

	@NotNull
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date releaseDate;

	@NotNull
	@Past
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date lastUpdatedDate;
	
	private String messageBody;
	

}
