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
@Table(name="notification")
@NamedQuery(name="Notification.findAll", query="SELECT n FROM Notification n")
public class Notification implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private NotificationPK id;

	public Notification() {
	}

	public NotificationPK getId() {
		return this.id;
	}

	public void setId(NotificationPK id) {
		this.id = id;
	}

}