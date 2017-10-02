package org.debugroom.wedding.app.model.message;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class GetMessagesResult implements Serializable{

	public GetMessagesResult(){}
	
	private static final long serialVersionUID = 7556250017174766401L;

	private List<Message> messages;
	private List<String> errorMessages;
	
}
