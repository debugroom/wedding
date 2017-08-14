package org.debugroom.wedding.app.model.management.request;

import java.util.List;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class Request {

	private String requestId;
	private String title;
	private String requestContents;
	private Date registratedDate;
	private List<User> checkedUsers;
	
}
