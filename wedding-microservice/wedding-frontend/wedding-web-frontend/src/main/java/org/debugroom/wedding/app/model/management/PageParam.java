package org.debugroom.wedding.app.model.management;

import java.io.Serializable;

import javax.validation.constraints.Min;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.Builder;

@AllArgsConstructor
@Builder
@Data
public class PageParam implements Serializable{

	private static final long serialVersionUID = 154651265564181379L;

	public PageParam(){
		this.page = new Integer(0);
		this.size = new Integer(10);
	}
	
	@Min(0)
	private Integer size;
	@Min(0)
	private Integer page;
}
