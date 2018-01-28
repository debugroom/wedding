package org.debugroom.wedding.app.model.message;

import java.io.Serializable;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class Group implements Serializable{

	private static final long serialVersionUID = 2841542586211232592L;

	public Group(){
	}
	
	private Long groupId;
	@NotNull
	@Size(min=1, max=256)
	@Pattern(regexp="^[^ =#$%&./<>?¥^¥~¥[¥]¥(¥)¥¥]+$")
	private String groupName;
	@Valid
	private List<User> users;
	
}
