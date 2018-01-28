package org.debugroom.wedding.app.model.message;

import java.io.Serializable;
import java.util.List;

import org.debugroom.wedding.domain.entity.message.MessageBoard;

import lombok.Builder;
import lombok.Data;
import lombok.AllArgsConstructor;

@AllArgsConstructor 
@Builder
@Data
public class ChatPortalResource implements Serializable {

	private static final long serialVersionUID = 5166416327889110863L;

	public ChatPortalResource(){
	}

	private List<MessageBoard> messageBoards;

}
