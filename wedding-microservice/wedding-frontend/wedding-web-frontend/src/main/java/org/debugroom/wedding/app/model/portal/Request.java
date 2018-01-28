package org.debugroom.wedding.app.model.portal;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class Request implements Serializable{

	private static final long serialVersionUID = -4703764867388459582L;

	public Request(){
	}
	
	private String requestId;
	private String title;
	private String requestContents;
	private Timestamp registratedDate;
	private Timestamp lastUpdatedDate;
	private List<RequestStatus> requestStatuses;
	
	
}

