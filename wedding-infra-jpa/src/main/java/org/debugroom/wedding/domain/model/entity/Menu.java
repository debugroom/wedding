package org.debugroom.wedding.domain.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.Set;


/**
 * The persistent class for the menu database table.
 * 
 */
@AllArgsConstructor
@Builder
@Entity
@Table(name="menu")
@NamedQuery(name="Menu.findAll", query="SELECT m FROM Menu m")
public class Menu implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="menu_id", unique=true, nullable=false, length=4)
	private String menuId;

	@Column(name="authority_level")
	private Integer authorityLevel;

	@Temporal(TemporalType.DATE)
	@Column(name="last_updated_date")
	private Date lastUpdatedDate;

	@Column(name="menu_name", length=2147483647)
	private String menuName;

	@Column(length=2147483647)
	private String url;

	@Temporal(TemporalType.DATE)
	@Column(name="usable_end_date")
	private Date usableEndDate;

	@Temporal(TemporalType.DATE)
	@Column(name="usable_start_date")
	private Date usableStartDate;

	@Version
	private Integer ver;

	//bi-directional many-to-one association to Function
	@OneToMany(mappedBy="menu")
	private Set<Function> fnctions;

	public Menu() {
	}

	public String getMenuId() {
		return this.menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	public Integer getAuthorityLevel() {
		return this.authorityLevel;
	}

	public void setAuthorityLevel(Integer authorityLevel) {
		this.authorityLevel = authorityLevel;
	}

	public Date getLastUpdatedDate() {
		return this.lastUpdatedDate;
	}

	public void setLastUpdatedDate(Date lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}

	public String getMenuName() {
		return this.menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
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

	public Set<Function> getFnctions() {
		return this.fnctions;
	}

	public void setFnctions(Set<Function> fnctions) {
		this.fnctions = fnctions;
	}

	public Function addFnction(Function fnction) {
		getFnctions().add(fnction);
		fnction.setMenu(this);

		return fnction;
	}

	public Function removeFnction(Function fnction) {
		getFnctions().remove(fnction);
		fnction.setMenu(null);

		return fnction;
	}

}
