package org.debugroom.wedding.app.model.message;

import java.io.Serializable;
import java.util.List;

import javax.validation.Valid;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class UpdateMessageBoardForm implements Serializable{

	private static final long serialVersionUID = -3189344152514075374L;

	public UpdateMessageBoardForm(){
	}

	public static interface UpdateMessageBoard{}
	
	@Valid
	private MessageBoard messageBoard;
	@Valid
	private List<User> checkedAddUsers;
	@Valid
	private List<User> checkedDeleteUsers;
	
	
}
