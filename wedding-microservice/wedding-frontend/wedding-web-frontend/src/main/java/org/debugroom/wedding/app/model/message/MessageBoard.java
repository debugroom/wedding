package org.debugroom.wedding.app.model.message;

import java.io.Serializable;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class MessageBoard implements Serializable{

	public MessageBoard(){
	}

	private static final long serialVersionUID = 417441630904687074L;

	@NotNull
	@Min(0)
	private Long messageBoardId;
	private String title;
	private Group group;

}
