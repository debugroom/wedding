package org.debugroom.wedding.app.web.management.request;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.Future;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.validation.groups.Default;

import org.debugroom.wedding.app.web.management.infomation.NewInfomationForm;
import org.debugroom.wedding.app.web.management.infomation.User;
import org.debugroom.wedding.app.web.management.infomation.NewInfomationForm.NewInfomationFormBuilder;
import org.debugroom.wedding.app.web.management.infomation.NewInfomationForm.SaveInfomation;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.Valid;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Data
@AllArgsConstructor
@Builder
public class NewRequestForm implements Serializable{
	
	private static final long serialVersionUID = -8760293380532963530L;

	public static interface ConfirmRequest{}
	public static interface SaveRequest{}
	
	public NewRequestForm(){
	}

	@NotNull(groups = {SaveRequest.class})
	@Size(min=4, max=4, groups = {SaveRequest.class})
	@Pattern(regexp = "[0-9]*", groups= {SaveRequest.class})
	private String requestId;
	
	@NotNull(groups = {ConfirmRequest.class, SaveRequest.class})
	@Size(min=1, max=256, groups = {ConfirmRequest.class, SaveRequest.class})
	private String title;
	
	@NotNull(groups = {SaveRequest.class})
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date registratedDate;
	
	@NotNull(groups = {ConfirmRequest.class, SaveRequest.class})
	private String requestContents;
	
	@Valid
	private List<User> checkedUsers;
	
	@NotNull(groups = {SaveRequest.class})
	@Pattern(regexp = "[a-zA-Z0-9¥.¥-]*", groups = {SaveRequest.class})
	private String type;

}
