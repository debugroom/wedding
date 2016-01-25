package org.debugroom.wedding.app.web.common.model;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;


import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Data
@AllArgsConstructor
@Builder
public class Address implements Serializable{

	private static final long serialVersionUID = 1L;

	public Address(){}

	@NotNull
	@Size(min = 7, max=7)
	@Pattern(regexp = "[0-9]*")
	private String postCd;
	@NotNull
	@Size(min = 0, max=256)
	private String address;

}
