package org.debugroom.wedding.app.model.management.request;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.debugroom.wedding.app.model.management.request.NewRequestForm.ConfirmRequest;
import org.debugroom.wedding.app.model.management.request.NewRequestForm.SaveRequest;
import org.debugroom.wedding.app.model.management.request.UpdateRequestForm.UpdateRequest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class User implements Serializable{

	private static final long serialVersionUID = 7690747875539503745L;

	public User(){
	}

	@Size(min=8, max=8, 
		groups= {ConfirmRequest.class, SaveRequest.class, UpdateRequest.class})
	@Pattern(regexp= "[0-9]*", groups= {ConfirmRequest.class, SaveRequest.class})
	private String userId;
	
	@Size(min=1, max=50, 
		groups= {ConfirmRequest.class, SaveRequest.class, UpdateRequest.class})
	private String firstName;
	
	@Size(min=1, max=50, 
		groups= {ConfirmRequest.class, SaveRequest.class, UpdateRequest.class})
	private String lastName;

	private List<RequestStatus> requestStatuses;
	
}

