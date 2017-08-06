package org.debugroom.wedding.external.model;

import java.io.Serializable;

import lombok.Data;
import lombok.Builder;
import lombok.AllArgsConstructor;

@Data
@Builder
@AllArgsConstructor
public class Address implements Serializable{

	private static final long serialVersionUID = -1001165143356142909L;

	public Address(){}
	
	private String pref;
	private String address;
	private String city;
	private String town;
	private String fullAddress;

}
