package org.debugroom.wedding.app.model.management.request;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Data
@AllArgsConstructor
@Builder
public class RequestDetailForm implements Serializable{

	private static final long serialVersionUID = 956326520274403427L;

	public RequestDetailForm(){
	}

	@NotNull
	@Size(min=4, max=4)
	@Pattern(regexp = "[0-9]*")
	private String requestId;

	@NotNull
	@Pattern(regexp = "[a-zA-Z0-9¥.¥-]*")
	private String type;

}
