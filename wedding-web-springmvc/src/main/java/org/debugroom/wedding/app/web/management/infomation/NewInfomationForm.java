package org.debugroom.wedding.app.web.management.infomation;

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

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.Valid;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Data
@AllArgsConstructor
@Builder
public class NewInfomationForm implements Serializable{

	private static final long serialVersionUID = 4933437679448850625L;

	public static interface ConfirmInfomation{}
	public static interface SaveInfomation{}
	
	public NewInfomationForm(){
	}

	@NotNull(groups = {SaveInfomation.class})
	@Size(min=8, max=8, groups = {SaveInfomation.class})
	@Pattern(regexp = "[0-9]*", groups = {SaveInfomation.class})
	private String infoId;

	@NotNull(groups = {ConfirmInfomation.class})
	@Size(min=1, max=50, groups = {ConfirmInfomation.class})
	@Pattern(regexp = "[a-zA-Z0-9¥.¥-¥_]*")
	private String infoName;
	
	@NotNull(groups = {SaveInfomation.class})
	@Size(min=1, max=50, groups = {SaveInfomation.class})
	@Pattern(regexp = "[a-zA-Z0-9¥.¥-¥_¥/]*")
	private String infoPagePath;

	@NotNull(groups = {ConfirmInfomation.class, SaveInfomation.class})
	@Size(min=1, max=256, groups = {ConfirmInfomation.class, SaveInfomation.class})
	private String title;
	
	@NotNull(groups = {SaveInfomation.class})
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date registratedDate;

	@NotNull(groups = {ConfirmInfomation.class, SaveInfomation.class})
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@Future(groups = {ConfirmInfomation.class, SaveInfomation.class})
	private Date releaseDate;

	@NotNull(groups = {ConfirmInfomation.class, SaveInfomation.class})
	private String messageBody;
	
	@Valid
	private List<User> checkedUsers;
	
	@Valid
	private List<User> users;
	
	@NotNull(groups = {SaveInfomation.class})
	@Pattern(regexp = "[a-zA-Z0-9¥.¥-]*", groups = {SaveInfomation.class})
	private String type;
	
}
