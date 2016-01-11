package org.debugroom.wedding.domain.model.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;

/**
 * The persistent class for the address database table.
 * 
 */
@AllArgsConstructor
@Builder
@Entity
@Table(name="request")
@NamedQuery(name="Request.findAll", query="SELECT r FROM Request r")
public class Request implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="request_id", unique=true, nullable=false, length=4)
	private String requestId;

	@Temporal(TemporalType.DATE)
	@Column(name="registrated_date")
	private Date registratedDate;

	@Column(name="request_contents", length=2147483647)
	private String requestContents;

	//bi-directional many-to-one association to RequestStatus
	@OneToMany(mappedBy="request")
	private Set<RequestStatus> requestStatuses;

	public Request() {
	}

	public String getRequestId() {
		return this.requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public Date getRegistratedDate() {
		return this.registratedDate;
	}

	public void setRegistratedDate(Date registratedDate) {
		this.registratedDate = registratedDate;
	}

	public String getRequestContents() {
		return this.requestContents;
	}

	public void setRequestContents(String requestContents) {
		this.requestContents = requestContents;
	}

	public Set<RequestStatus> getRequestStatuses() {
		return this.requestStatuses;
	}

	public void setRequestStatuses(Set<RequestStatus> requestStatuses) {
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