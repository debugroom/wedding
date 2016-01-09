package org.debugroom.wedding.domain.model.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


import lombok.AllArgsConstructor;
import lombok.Builder;

/**
 * The persistent class for the address database table.
 * 
 */
@AllArgsConstructor
@Builder
@Entity
@Table(name="fnction")
@NamedQuery(name="Function.findAll", query="SELECT f FROM Function f")
public class Function implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="function_id", unique=true, nullable=false, length=4)
	private String functionId;

	@Column(name="authority_level")
	private Integer authorityLevel;

	@Column(name="function_name", length=100)
	private String functionName;

	@Temporal(TemporalType.DATE)
	@Column(name="usable_end_date")
	private Date usableEndDate;

	@Temporal(TemporalType.DATE)
	@Column(name="usable_start_date")
	private Date usableStartDate;

	//bi-directional many-to-one association to Menu
	@ManyToOne
	@JoinColumn(name="menu_id", nullable=false)
	private Menu menu;

	public Function() {
	}

	public String getFunctionId() {
		return this.functionId;
	}

	public void setFunctionId(String functionId) {
		this.functionId = functionId;
	}

	public Integer getAuthorityLevel() {
		return this.authorityLevel;
	}

	public void setAuthorityLevel(Integer authorityLevel) {
		this.authorityLevel = authorityLevel;
	}

	public String getFunctionName() {
		return this.functionName;
	}

	public void setFunctionName(String functionName) {
		this.functionName = functionName;
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

	public Menu getMenu() {
		return this.menu;
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
	}

}