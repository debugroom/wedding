package org.debugroom.wedding.app.web.management.infomation;

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
public class DeleteInfomationForm implements Serializable{

	private static final long serialVersionUID = -3259538983751530007L;

	public DeleteInfomationForm(){
	}
	
	@NotNull
	@Size(min=8, max=8)
	@Pattern(regexp = "[0-9]*")
	private String infoId;
	
	@NotNull
	@Pattern(regexp = "[a-zA-Z0-9¥.¥-]*")
	private String type;
}
