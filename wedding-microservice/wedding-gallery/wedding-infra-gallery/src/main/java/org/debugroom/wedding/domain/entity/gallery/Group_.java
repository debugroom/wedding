package org.debugroom.wedding.domain.entity.gallery;

import java.sql.Timestamp;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2017-07-31T01:09:20.886+0900")
@StaticMetamodel(Group.class)
public class Group_ {
	public static volatile SingularAttribute<Group, String> groupId;
	public static volatile SingularAttribute<Group, String> groupName;
	public static volatile SingularAttribute<Group, Timestamp> lastUpdatedDate;
	public static volatile SingularAttribute<Group, Integer> ver;
	public static volatile SetAttribute<Group, GroupFolder> groupFolders;
	public static volatile SetAttribute<Group, GroupNotification> groupNotifications;
	public static volatile SetAttribute<Group, GroupVisibleMovie> groupVisibleMovies;
	public static volatile SetAttribute<Group, GroupVisiblePhoto> groupVisiblePhotos;
}
