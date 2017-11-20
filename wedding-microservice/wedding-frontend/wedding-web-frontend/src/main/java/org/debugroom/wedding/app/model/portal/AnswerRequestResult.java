package org.debugroom.wedding.app.model.portal;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class AnswerRequestResult implements Serializable{

	private static final long serialVersionUID = -8385142489030368013L;

	public AnswerRequestResult(){
	}
	
	private boolean isApproved;

}
