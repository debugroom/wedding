package org.debugroom.wedding.app.model.profile;

import java.io.Serializable;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Data
@AllArgsConstructor
@Builder
public class NotificationPK implements Serializable{

	private static final long serialVersionUID = -254495776936956515L;

	public NotificationPK(){
	}

	private String infoId;
	private String userId;

}
