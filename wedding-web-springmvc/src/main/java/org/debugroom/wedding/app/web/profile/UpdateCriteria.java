package org.debugroom.wedding.app.web.profile;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class UpdateCriteria implements Serializable{

	private static final long serialVersionUID = 1L;

	private String updateParam;
	
}
