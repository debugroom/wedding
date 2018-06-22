package org.debugroom.wedding.app.model.management.request;

import java.io.Serializable;
import java.util.List;
import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class Request implements Serializable {

	public Request(){}

	private String requestId;
	private String title;
	private String requestContents;
	private Timestamp registratedDate;
	private List<User> checkedUsers;
	
}
