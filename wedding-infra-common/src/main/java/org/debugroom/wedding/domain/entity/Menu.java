package org.debugroom.wedding.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Set;


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

	@Column(name="authority_level")
	private Integer authorityLevel;

	@Column(name="last_updated_date")
	private Timestamp lastUpdatedDate;

	@Column(name="menu_name")
	private String menuName;

	private String url;

	@Column(name="usable_end_date")
	private Timestamp usableEndDate;

	@Column(name="usable_start_date")
	private Timestamp usableStartDate;

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

	public Timestamp getLastUpdatedDate() {
		return this.lastUpdatedDate;
	}

	public void setLastUpdatedDate(Timestamp lastUpdatedDate) {
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

	public Timestamp getUsableEndDate() {
		return this.usableEndDate;
	}

	public void setUsableEndDate(Timestamp usableEndDate) {
		this.usableEndDate = usableEndDate;
	}

	public Timestamp getUsableStartDate() {
		return this.usableStartDate;
	}

	public void setUsableStartDate(Timestamp usableStartDate) {
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
