package org.debugroom.wedding.app.model.portal;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class AnswerRequest implements Serializable{

	private static final long serialVersionUID = 3352364419603249541L;

	public AnswerRequest(){
	}
	
	private String userId;
	private String requestId;
	private boolean isApproved;

}
