package org.debugroom.wedding.app.web.management.request;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.validation.groups.Default;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class User implements Serializable{

	private static final long serialVersionUID = 2971084986408424254L;

	public User(){
	}
	
	@Size(min=8, max=8, groups = {Default.class})
	@Pattern(regexp = "[0-9]*", groups = {Default.class})
	private String userId;

	@Size(min=1, max=50, groups = {Default.class})
	private String userName;
	
	
	
}
