package org.debugroom.wedding.app.model;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class LogPK implements Serializable{

	private static final long serialVersionUID = -3760759896074471617L;

	public LogPK(){
	}
	private String userId;
	private Date timeStamp;
	
}
