package org.debugroom.wedding.app.model.message;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class ChatPortalResource implements Serializable{

	private static final long serialVersionUID = 1718723394184449863L;

	public ChatPortalResource(){
	}
	
	private List<MessageBoard> messageBoards;

}
