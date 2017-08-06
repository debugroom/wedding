package org.debugroom.wedding.domain.entity.gallery;

import java.sql.Timestamp;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2017-07-31T01:09:20.920+0900")
@StaticMetamodel(UserRelatedFolder.class)
public class UserRelatedFolder_ {
	public static volatile SingularAttribute<UserRelatedFolder, UserRelatedFolderPK> id;
	public static volatile SingularAttribute<UserRelatedFolder, Timestamp> lastUpdatedDate;
	public static volatile SingularAttribute<UserRelatedFolder, Integer> ver;
	public static volatile SingularAttribute<UserRelatedFolder, Folder> folder;
	public static volatile SingularAttribute<UserRelatedFolder, User> usr;
}
