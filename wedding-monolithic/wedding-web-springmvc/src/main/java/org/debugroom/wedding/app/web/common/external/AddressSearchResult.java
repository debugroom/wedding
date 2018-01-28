package org.debugroom.wedding.app.web.common.external;

import java.util.List;

import org.debugroom.wedding.common.external.model.Address;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Builder;

@AllArgsConstructor
@Builder
@Data
public class AddressSearchResult {

	public AddressSearchResult(){}

	private List<String> messages;
	private Address address;
	
}
