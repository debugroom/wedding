package org.debugroom.wedding.app.model.management.user;

import java.io.Serializable;
import java.util.List;

import org.debugroom.wedding.external.model.Address;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Builder;

@AllArgsConstructor
@Builder
@Data
public class AddressSearchResult implements Serializable{

	private static final long serialVersionUID = -1109630054447254323L;

	public AddressSearchResult(){
	}
	
	private List<String> messages;
	private Address address;

}
