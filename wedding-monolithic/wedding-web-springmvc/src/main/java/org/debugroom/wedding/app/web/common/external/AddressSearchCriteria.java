package org.debugroom.wedding.app.web.common.external;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class AddressSearchCriteria implements Serializable{
	
	private static final long serialVersionUID = -3084962712607242164L;
	
	public static interface SearchAddress{}

	@NotNull(groups = {SearchAddress.class})
	@Size(min = 8, max=8, groups = {SearchAddress.class})
	@Pattern(regexp = "[-0-9]*", groups = {SearchAddress.class})
	private String zipCode;

}
