package org.debugroom.wedding.app.model.portal;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class RequestStatus implements Serializable{

	private static final long serialVersionUID = -3657532482208571953L;

	public RequestStatus(){
	}
	
	private RequestStatusPK id;
	private Boolean isAnswered;
	private Boolean isApproved;
	
}
