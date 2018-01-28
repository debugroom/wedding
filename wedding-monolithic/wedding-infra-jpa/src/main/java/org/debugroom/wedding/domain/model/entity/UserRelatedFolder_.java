package org.debugroom.wedding.domain.model.entity;

import java.sql.Timestamp;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2016-05-02T02:07:04.695+0900")
@StaticMetamodel(UserRelatedFolder.class)
public class UserRelatedFolder_ {
	public static volatile SingularAttribute<UserRelatedFolder, UserRelatedFolderPK> id;
	public static volatile SingularAttribute<UserRelatedFolder, Timestamp> lastUpdatedDate;
	public static volatile SingularAttribute<UserRelatedFolder, Integer> ver;
	public static volatile SingularAttribute<UserRelatedFolder, Folder> folder;
	public static volatile SingularAttribute<UserRelatedFolder, User> usr;
}
