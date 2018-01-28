package org.debugroom.wedding.app.web.management.infomation;

import java.io.Serializable;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.validation.groups.Default;
import javax.validation.Valid;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Data
@AllArgsConstructor
@Builder
public class InfomationDatailForm implements Serializable{

	private static final long serialVersionUID = 8031733349099599602L;

	public static interface GetInfomation{}

	@NotNull(groups = {GetInfomation.class})
	@Size(min=8, max=8, groups={GetInfomation.class})
	@Pattern(regexp = "[0-9]*", groups={GetInfomation.class})
	private String infoId;

	@NotNull(groups = {GetInfomation.class})
	@Pattern(regexp = "[a-zA-Z0-9¥.¥-]*", groups = {GetInfomation.class})
	private String type;

}
