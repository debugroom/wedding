package org.debugroom.wedding.domain.model.entity;

import java.sql.Timestamp;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2016-05-02T02:19:55.076+0900")
@StaticMetamodel(User.class)
public class User_ {
	public static volatile SingularAttribute<User, String> userId;
	public static volatile SingularAttribute<User, Integer> authorityLevel;
	public static volatile SingularAttribute<User, String> imageFilePath;
	public static volatile SingularAttribute<User, Timestamp> lastLoginDate;
	public static volatile SingularAttribute<User, Timestamp> lastUpdatedDate;
	public static volatile SingularAttribute<User, String> loginId;
	public static volatile SingularAttribute<User, String> userName;
	public static volatile SingularAttribute<User, Integer> ver;
	public static volatile SingularAttribute<User, Address> address;
	public static volatile SetAttribute<User, Affiliation> affiliations;
	public static volatile SetAttribute<User, Credential> credentials;
	public static volatile SetAttribute<User, Email> emails;
	public static volatile SetAttribute<User, Folder> folders;
	public static volatile SetAttribute<User, Notification> notifications;
	public static volatile SetAttribute<User, RequestStatus> requestStatuses;
	public static volatile SetAttribute<User, UserRelatedFolder> userRelatedFolders;
	public static volatile SetAttribute<User, Group> grps;
	public static volatile SetAttribute<User, MovieRelatedUser> movieRelatedUsers;
	public static volatile SetAttribute<User, PhotoRelatedUser> photoRelatedUsers;
}
