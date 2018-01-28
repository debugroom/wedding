package org.debugroom.wedding.app.web.galley;

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
public class Media implements Serializable{

	private static final long serialVersionUID = -4712548509692506866L;

	public Media(){
	}
	
	@NotNull
	@Size(min=10, max=10)
	@Pattern(regexp = "[0-9]*")
	private String mediaId;
	
}
