package org.debugroom.wedding.app.model.management;

import java.io.Serializable;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Data
@AllArgsConstructor
@Builder
public class Address implements Serializable{
	
	private static final long serialVersionUID = -3255066818918617151L;

	public Address(){
	}
	
	private String postCd;
	private String address;

}

