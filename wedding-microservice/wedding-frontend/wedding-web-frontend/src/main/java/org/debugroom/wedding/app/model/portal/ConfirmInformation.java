package org.debugroom.wedding.app.model.portal;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class ConfirmInformation implements Serializable{

	private static final long serialVersionUID = -2974097725449880879L;

	public ConfirmInformation(){
	}
	
	private String infoId;
	private String userId;
	private boolean isWatched;

}
