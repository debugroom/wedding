package org.debugroom.wedding.app.model.management.information;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.Builder;

@AllArgsConstructor
@Builder
@Data
public class Information implements Serializable{
	
	private static final long serialVersionUID = 455896341515286495L;

	public static interface GetMessageBody{}
	public static interface UpdateInformation{}

	public Information(){
	}
	
	@NotNull(groups={GetMessageBody.class, UpdateInformation.class})
	@Size(min=8, max=8, groups={GetMessageBody.class, UpdateInformation.class})
	@Pattern(regexp = "[0-9]*", groups={GetMessageBody.class, UpdateInformation.class})
	private String infoId;
	@NotNull(groups={GetMessageBody.class, UpdateInformation.class})
	@Size(min=1, max=1024, groups={GetMessageBody.class, UpdateInformation.class})
	@Pattern(regexp = "[-¥.¥_¥/a-zA-Z0-9]*", groups={GetMessageBody.class, UpdateInformation.class})
	private String infoPagePath;
	@NotNull(groups={UpdateInformation.class})
	@Size(min=1, max=256, groups={UpdateInformation.class})
	private String title;
	@NotNull(groups={UpdateInformation.class})
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@Past(groups={UpdateInformation.class})
	private Date registratedDate;
	@NotNull(groups={UpdateInformation.class})
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date releaseDate;
	@NotNull(groups={UpdateInformation.class})
	@Past(groups={UpdateInformation.class})
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date lastUpdatedDate;
	private String messageBody;
	
}
