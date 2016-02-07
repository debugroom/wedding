package org.debugroom.wedding.domain.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the fnction database table.
 * 
 */
@AllArgsConstructor
@Builder
@Entity
@Table(name="fnction")
@NamedQuery(name="Function.findAll", query="SELECT f FROM Function f")
public class Function implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private FunctionPK id;

	@Column(name="authority_level")
	private Integer authorityLevel;

	@Column(name="function_name", length=100)
	private String functionName;

	@Temporal(TemporalType.DATE)
	@Column(name="last_updated_date")
	private Date lastUpdatedDate;

	@Column(length=2147483647)
	private String url;

	@Temporal(TemporalType.DATE)
	@Column(name="usable_end_date")
	private Date usableEndDate;

	@Temporal(TemporalType.DATE)
	@Column(name="usable_start_date")
	private Date usableStartDate;

	private Integer ver;

	//bi-directional many-to-one association to Menu
	@ManyToOne
	@JoinColumn(name="menu_id", nullable=false, insertable=false, updatable=false)
	private Menu menu;

	public Function() {
	}

	public FunctionPK getId() {
		return this.id;
	}

	public void setId(FunctionPK id) {
		this.id = id;
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

	public Date getLastUpdatedDate() {
		return this.lastUpdatedDate;
	}

	public void setLastUpdatedDate(Date lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
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

	public Integer getVer() {
		return this.ver;
	}

	public void setVer(Integer ver) {
		this.ver = ver;
	}

	public Menu getMenu() {
		return this.menu;
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
	}

}
