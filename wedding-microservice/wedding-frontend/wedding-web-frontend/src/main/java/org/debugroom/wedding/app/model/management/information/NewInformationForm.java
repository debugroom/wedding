package org.debugroom.wedding.app.model.management.information;

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
import javax.validation.Valid;

import org.springframework.format.annotation.DateTimeFormat;


import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Data
@AllArgsConstructor
@Builder
public class NewInformationForm implements Serializable{

	private static final long serialVersionUID = 4820587496019971817L;

	public static interface ConfirmInformation{}
	public static interface SaveInformation{}
	
	public NewInformationForm(){
	}

	@NotNull(groups = {SaveInformation.class})
	@Size(min=8, max=8, groups = {SaveInformation.class})
	@Pattern(regexp = "[0-9]*", groups = {SaveInformation.class})
	private String infoId;

	@NotNull(groups = {ConfirmInformation.class})
	@Size(min=1, max=50, groups = {ConfirmInformation.class})
	@Pattern(regexp = "[a-zA-Z0-9¥.¥-¥_]*")
	private String infoName;
	
	@NotNull(groups = {SaveInformation.class})
	@Size(min=1, max=50, groups = {SaveInformation.class})
	@Pattern(regexp = "[a-zA-Z0-9¥.¥-¥_¥/]*")
	private String infoPagePath;

	@NotNull(groups = {ConfirmInformation.class, SaveInformation.class})
	@Size(min=1, max=256, groups = {ConfirmInformation.class, SaveInformation.class})
	private String title;
	
	@NotNull(groups = {SaveInformation.class})
	@DateTimeFormat(pattern="yyyy/MM/dd HH:mm:ss")
	private Date registratedDate;

	@NotNull(groups = {ConfirmInformation.class, SaveInformation.class})
	@DateTimeFormat(pattern="yyyy/MM/dd HH:mm:ss")
//	@Future(groups = {ConfirmInformation.class, SaveInformation.class})
	private Date releaseDate;

	@NotNull(groups = {ConfirmInformation.class, SaveInformation.class})
	private String messageBody;
	
	@Valid
	private List<User> checkedUsers;
	
	@Valid
	private List<User> users;
	
	@NotNull(groups = {SaveInformation.class})
	@Pattern(regexp = "[a-zA-Z0-9¥.¥-]*", groups = {SaveInformation.class})
	private String type;
	
}
