package org.debugroom.wedding.domain.entity.gallery;

import java.sql.Timestamp;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2017-07-31T01:09:20.919+0900")
@StaticMetamodel(User.class)
public class User_ {
	public static volatile SingularAttribute<User, String> userId;
	public static volatile SingularAttribute<User, Integer> authorityLevel;
	public static volatile SingularAttribute<User, String> firstName;
	public static volatile SingularAttribute<User, String> imageFilePath;
	public static volatile SingularAttribute<User, Boolean> isBrideSide;
	public static volatile SingularAttribute<User, Timestamp> lastLoginDate;
	public static volatile SingularAttribute<User, String> lastName;
	public static volatile SingularAttribute<User, Timestamp> lastUpdatedDate;
	public static volatile SingularAttribute<User, String> loginId;
	public static volatile SingularAttribute<User, Integer> ver;
	public static volatile SetAttribute<User, Folder> folders;
	public static volatile SetAttribute<User, MovieRelatedUser> movieRelatedUsers;
	public static volatile SetAttribute<User, PhotoRelatedUser> photoRelatedUsers;
	public static volatile SetAttribute<User, UserRelatedFolder> userRelatedFolders;
}
