package org.debugroom.wedding.app.model.message;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class UpdateMessageBoardResult implements Serializable{

	private static final long serialVersionUID = 3560609339240120573L;

	public UpdateMessageBoardResult(){
	}

	private String userId;
	private String requestContextPath;
	private MessageBoard messageBoard;
	private List<String> messages;

}
