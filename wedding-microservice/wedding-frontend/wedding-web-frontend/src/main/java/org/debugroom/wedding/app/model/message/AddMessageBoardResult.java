package org.debugroom.wedding.app.model.message;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class AddMessageBoardResult implements Serializable{

	private static final long serialVersionUID = -1636641288751179488L;

	public AddMessageBoardResult(){
	}

	private String userId;
	private String requestContextPath;
	private MessageBoard messageBoard;
	private List<String> messages;

}
