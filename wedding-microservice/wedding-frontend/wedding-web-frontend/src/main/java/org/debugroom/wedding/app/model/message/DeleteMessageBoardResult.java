package org.debugroom.wedding.app.model.message;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class DeleteMessageBoardResult implements Serializable{

	private static final long serialVersionUID = -7746848366803745194L;

	public DeleteMessageBoardResult(){
	}
	
	private MessageBoard messageBoard;
	private List<String> messages;
	
}
