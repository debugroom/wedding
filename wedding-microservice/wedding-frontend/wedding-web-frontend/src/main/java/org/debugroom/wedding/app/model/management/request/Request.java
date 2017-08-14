package org.debugroom.wedding.app.model.management.request;

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
public class Request implements Serializable{

	private static final long serialVersionUID = 5252990361696476778L;

	public Request(){
	}
	
	@NotNull
	@Size(min=4, max=4)
	@Pattern(regexp = "[0-9]*")
	private String requestId;
	
	@NotNull
	@Size(min=1, max=256)
	private String title;
	
	@NotNull
	@DateTimeFormat(pattern="yyyy/MM/dd HH:mm:ss")
	private Date registratedDate;
	
	@NotNull
	@Past
	@DateTimeFormat(pattern="yyyy/MM/dd HH:mm:ss")
	private Date lastUpdatedDate;

	private String requestContents;
}
