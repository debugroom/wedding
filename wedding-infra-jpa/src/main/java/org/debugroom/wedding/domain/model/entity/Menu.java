package org.debugroom.wedding.domain.model.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;

/**
 * The persistent class for the menu database table.
 * 
 */
@AllArgsConstructor
@Builder
@Entity
@NamedQuery(name="Menu.findAll", query="SELECT m FROM Menu m")
public class Menu implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="menu_id")
	private String menuId;

	@Column(name="function_id")
	private String functionId;

	@Column(name="menu_name")
	private String menuName;

	@Temporal(TemporalType.DATE)
	@Column(name="usable_end_date")
	private Date usableEndDate;

	@Temporal(TemporalType.DATE)
	@Column(name="usable_start_date")
	private Date usableStartDate;

	public Menu() {
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

	public String getMenuName() {
		return this.menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public Date getUsableEndDate() {
		return this.usableEndDate;
	}

	public void setUsableEndDate(Date usableEndDate) {
		this.usableEndDate = usableEndDate;
	}

	public Date getUsableStartDate() {
		return this.usableStartDate;
	}

	public void setUsableStartDate(Date usableStartDate) {
		this.usableStartDate = usableStartDate;
	}

}