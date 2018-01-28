package org.debugroom.wedding.app.web.basic;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class Request implements Serializable{

	private static final long serialVersionUID = -7107800267182138992L;

	public Request(){
	}
	
	@NotNull
	@Size(min=4, max=4)
	@Pattern(regexp = "[0-9]*")
	private String requestId;
}
