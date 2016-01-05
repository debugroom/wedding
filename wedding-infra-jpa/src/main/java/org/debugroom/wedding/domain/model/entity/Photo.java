package org.debugroom.wedding.domain.model.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Set;


/**
 * The persistent class for the photo database table.
 * 
 */
@Entity
@NamedQuery(name="Photo.findAll", query="SELECT p FROM Photo p")
public class Photo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="photo_id")
	private String photoId;

	@Column(name="file_path")
	private String filePath;

	private Boolean iscontroled;

	//bi-directional many-to-many association to Group
	@ManyToMany
	@JoinTable(
		name="group_visible_photo"
		, joinColumns={
			@JoinColumn(name="photo_id")
			}
		, inverseJoinColumns={
			@JoinColumn(name="group_id")
			}
		)
	private Set<Group> grps;

	//bi-directional many-to-one association to Message
	@OneToMany(mappedBy="photo")
	private Set<Message> messages;

	public Photo() {
	}

	public String getPhotoId() {
		return this.photoId;
	}

	public void setPhotoId(String photoId) {
		this.photoId = photoId;
	}

	public String getFilePath() {
		return this.filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public Boolean getIscontroled() {
		return this.iscontroled;
	}

	public void setIscontroled(Boolean iscontroled) {
		this.iscontroled = iscontroled;
	}

	public Set<Group> getGrps() {
		return this.grps;
	}

	public void setGrps(Set<Group> grps) {
		this.grps = grps;
	}

	public Set<Message> getMessages() {
		return this.messages;
	}

	public void setMessages(Set<Message> messages) {
		this.messages = messages;
	}

	public Message addMessage(Message message) {
		getMessages().add(message);
		message.setPhoto(this);

		return message;
	}

	public Message removeMessage(Message message) {
		getMessages().remove(message);
		message.setPhoto(null);

		return message;
	}

}