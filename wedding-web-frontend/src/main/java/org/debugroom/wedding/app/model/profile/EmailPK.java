package org.debugroom.wedding.app.model.profile;

import java.io.Serializable;

import javax.validation.constraints.Min;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.Builder;

@AllArgsConstructor
@Builder
@Data
public class EmailPK implements Serializable{

	private static final long serialVersionUID = -7140186735287573981L;

	public EmailPK(){
	}
	private String userId;
	@Min(0)
	private Integer emailId;

}
