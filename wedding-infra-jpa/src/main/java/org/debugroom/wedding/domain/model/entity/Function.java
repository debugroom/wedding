package org.debugroom.wedding.domain.model.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the fnction database table.
 * 
 */
@Entity
@Table(name="fnction")
@NamedQuery(name="Function.findAll", query="SELECT f FROM Function f")
public class Function implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name="function_id")
	private String functionId;

	@Column(name="function_name")
	private String functionName;

	@Column(name="menu_id")
	private String menuId;

	public Function() {
	}

	public String getFunctionId() {
		return this.functionId;
	}

	public void setFunctionId(String functionId) {
		this.functionId = functionId;
	}

	public String getFunctionName() {
		return this.functionName;
	}

	public void setFunctionName(String functionName) {
		this.functionName = functionName;
	}

	public String getMenuId() {
		return this.menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

}