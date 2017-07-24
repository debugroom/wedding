package org.debugroom.wedding.app.model.management.user;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class AddressSearchCriteria implements Serializable{
	
	private static final long serialVersionUID = 3332737131519024229L;

	public static interface SearchAddress{}
	
	public AddressSearchCriteria(){
	}

	@NotNull(groups = {SearchAddress.class})
	@Size(min = 8, max=8, groups = {SearchAddress.class})
	@Pattern(regexp = "[-0-9]*", groups = {SearchAddress.class})
	private String zipCode;

}
