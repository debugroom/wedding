package org.debugroom.wedding.app.model.message;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class MessagePK implements Serializable{

	private static final long serialVersionUID = 3450665306851311187L;

	public MessagePK(){
	}

	private Long messageBoardId;
	private Long messageNo;
	
}
