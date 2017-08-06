package org.debugroom.wedding.domain.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the request_status database table.
 * 
 */
@AllArgsConstructor
@Builder
@Entity
@Table(name="request_status")
@NamedQuery(name="RequestStatus.findAll", query="SELECT r FROM RequestStatus r")
public class RequestStatus implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private RequestStatusPK id;

	@Column(name="is_answered")
	private Boolean isAnswered;

	@Column(name="is_approved")
	private Boolean isApproved;

	@Column(name="last_updated_date")
	private Timestamp lastUpdatedDate;

	@Column(length=2147483647)
	private String response;

	@Version
	private Integer ver;

	//bi-directional many-to-one association to Request
	@ManyToOne
	@JoinColumn(name="request_id", nullable=false, insertable=false, updatable=false)
	private Request request;

	//bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name="user_id", nullable=false, insertable=false, updatable=false)
	private User usr;

	public RequestStatus() {
	}

	public RequestStatusPK getId() {
		return this.id;
	}

	public void setId(RequestStatusPK id) {
		this.id = id;
	}

	public Boolean getIsAnswered() {
		return this.isAnswered;
	}

	public void setIsAnswered(Boolean isAnswered) {
		this.isAnswered = isAnswered;
	}

	public Boolean getIsApproved() {
		return this.isApproved;
	}

	public void setIsApproved(Boolean isApproved) {
		this.isApproved = isApproved;
	}

	public Timestamp getLastUpdatedDate() {
		return this.lastUpdatedDate;
	}

	public void setLastUpdatedDate(Timestamp lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}

	public String getResponse() {
		return this.response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

	public Integer getVer() {
		return this.ver;
	}

	public void setVer(Integer ver) {
		this.ver = ver;
	}

	public Request getRequest() {
		return this.request;
	}

	public void setRequest(Request request) {
		this.request = request;
	}

	public User getUsr() {
		return this.usr;
	}

	public void setUsr(User usr) {
		this.usr = usr;
	}

}
