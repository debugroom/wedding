package org.debugroom.wedding.domain.model.entity;

import java.io.Serializable;
import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;

/**
 * The persistent class for the address database table.
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

	@Column(length=2147483647)
	private String response;

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

	public String getResponse() {
		return this.response;
	}

	public void setResponse(String response) {
		this.response = response;
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