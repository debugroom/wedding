package org.debugroom.wedding.app.model.management.information;

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
public class InformationDetailForm implements Serializable{

	private static final long serialVersionUID = -5418009000993331230L;

	public static interface GetInformation{}
	
	public InformationDetailForm(){
	}

	@NotNull(groups = {GetInformation.class})
	@Size(min=8, max=8, groups={GetInformation.class})
	@Pattern(regexp = "[0-9]*", groups={GetInformation.class})
	private String infoId;
	
	@NotNull(groups = {GetInformation.class})
	@Pattern(regexp = "[a-zA-Z0-9¥.¥-]*", groups = {GetInformation.class})
	private String type;
	
}
