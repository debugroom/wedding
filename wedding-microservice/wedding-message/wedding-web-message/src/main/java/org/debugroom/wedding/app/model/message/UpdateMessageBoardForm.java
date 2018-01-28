package org.debugroom.wedding.app.model.message;

import java.io.Serializable;
import java.util.List;

import org.debugroom.wedding.domain.entity.message.MessageBoard;
import org.debugroom.wedding.domain.entity.message.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class UpdateMessageBoardForm implements Serializable  {

	private static final long serialVersionUID = -9207622365272522716L;
	
	public UpdateMessageBoardForm(){
	}

	private MessageBoard messageBoard;
	private List<User> checkedAddUsers;
	private List<User> checkedDeleteUsers;
	
}
