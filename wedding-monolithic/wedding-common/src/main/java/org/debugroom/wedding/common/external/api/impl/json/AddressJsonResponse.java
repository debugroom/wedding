package org.debugroom.wedding.common.external.api.impl.json;

import org.debugroom.wedding.common.external.model.Address;

import lombok.Data;

@Data
public class AddressJsonResponse {

	private String code;
	private String message;
	private Address data;
	
}
