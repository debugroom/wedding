package org.debugroom.wedding.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the fnction database table.
 * 
 */
@AllArgsConstructor
@Builder
@Embeddable
public class FunctionPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="menu_id", insertable=false, updatable=false)
	private String menuId;

	@Column(name="function_id")
	private String functionId;

	public FunctionPK() {
	}
	public String getMenuId() {
		return this.menuId;
	}
	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}
	public String getFunctionId() {
		return this.functionId;
	}
	public void setFunctionId(String functionId) {
		this.functionId = functionId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof FunctionPK)) {
			return false;
		}
		FunctionPK castOther = (FunctionPK)other;
		return 
			this.menuId.equals(castOther.menuId)
			&& this.functionId.equals(castOther.functionId);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.menuId.hashCode();
		hash = hash * prime + this.functionId.hashCode();
		
		return hash;
	}
}
