package org.debugroom.wedding.app.model.message;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class UpdateMessageBoardResource implements Serializable{

	private static final long serialVersionUID = -5595238475962019637L;

	public UpdateMessageBoardResource(){
	}
	
	private String requestContextPath;
	private MessageBoard messageBoard; 
	private List<String> messages;
	private List<User> notBelongUsers;

}
