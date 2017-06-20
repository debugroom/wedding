package org.debugroom.wedding.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the request database table.
 * 
 */
@AllArgsConstructor
@Builder
@Entity
@NamedQuery(name="Request.findAll", query="SELECT r FROM Request r")
public class Request implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="request_id")
	private String requestId;

	@Column(name="last_updated_date")
	private Timestamp lastUpdatedDate;

	@Column(name="registrated_date")
	private Timestamp registratedDate;

	@Column(name="request_contents")
	private String requestContents;

	private String title;

	@Version
	private Integer ver;

	//bi-directional many-to-one association to RequestStatus
	@OneToMany(mappedBy="request")
	private List<RequestStatus> requestStatuses;

	public Request() {
	}

	public String getRequestId() {
		return this.requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public Timestamp getLastUpdatedDate() {
		return this.lastUpdatedDate;
	}

	public void setLastUpdatedDate(Timestamp lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}

	public Timestamp getRegistratedDate() {
		return this.registratedDate;
	}

	public void setRegistratedDate(Timestamp registratedDate) {
		this.registratedDate = registratedDate;
	}

	public String getRequestContents() {
		return this.requestContents;
	}

	public void setRequestContents(String requestContents) {
		this.requestContents = requestContents;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getVer() {
		return this.ver;
	}

	public void setVer(Integer ver) {
		this.ver = ver;
	}

	public List<RequestStatus> getRequestStatuses() {
		return this.requestStatuses;
	}

	public void setRequestStatuses(List<RequestStatus> requestStatuses) {
		this.requestStatuses = requestStatuses;
	}

	public RequestStatus addRequestStatus(RequestStatus requestStatus) {
		getRequestStatuses().add(requestStatus);
		requestStatus.setRequest(this);

		return requestStatus;
	}

	public RequestStatus removeRequestStatus(RequestStatus requestStatus) {
		getRequestStatuses().remove(requestStatus);
		requestStatus.setRequest(null);

		return requestStatus;
	}

}
