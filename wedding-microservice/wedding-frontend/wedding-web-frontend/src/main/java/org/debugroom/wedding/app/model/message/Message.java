package org.debugroom.wedding.app.model.message;

import java.io.Serializable;

import javax.validation.constraints.Pattern;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class Message implements Serializable{

	private static final long serialVersionUID = 1505078505696614052L;

	public Message(){}
	
	private MessagePK messagepk;
	@Pattern(regexp="^[^ =#$./<>?¥^¥~¥[¥]¥¥]+$")
	private String comment;
	private User user;
	private int likeCount;

}
