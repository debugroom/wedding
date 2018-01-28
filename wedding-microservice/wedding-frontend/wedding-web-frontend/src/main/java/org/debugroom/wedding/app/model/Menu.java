package org.debugroom.wedding.app.model;

import java.io.Serializable;
import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class Menu implements Serializable{

	private static final long serialVersionUID = 3639949927133841246L;

	public Menu(){
	}

	private String menuId;
	private Integer authorityLevel;
	private Timestamp lastUpdatedDate;
	private String menuName;
	private String url;
	private boolean pathvariables;
	private Timestamp usableEndDate;
	private Timestamp usableStartDate;
	
}
