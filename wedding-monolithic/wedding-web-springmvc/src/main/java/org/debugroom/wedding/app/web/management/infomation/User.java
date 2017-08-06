package org.debugroom.wedding.app.web.management.infomation;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.validation.groups.Default;

import org.debugroom.wedding.app.web.management.infomation.NewInfomationForm.ConfirmInfomation;
import org.debugroom.wedding.app.web.management.infomation.NewInfomationForm.SaveInfomation;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class User implements Serializable{

	private static final long serialVersionUID = -2331240046509411761L;

	public User(){
	}
	
	@NotNull(groups = {Default.class})
	@Size(min=8, max=8, groups = {Default.class, ConfirmInfomation.class, SaveInfomation.class})
	@Pattern(regexp = "[0-9]*", groups = {Default.class, ConfirmInfomation.class, SaveInfomation.class})
	private String userId;

	@NotNull(groups = {Default.class})
	@Size(min=1, max=50, groups = {Default.class, ConfirmInfomation.class, SaveInfomation.class})
	private String userName;

}
