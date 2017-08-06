package org.debugroom.wedding.domain.entity.gallery;

import java.sql.Timestamp;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2017-07-31T01:09:20.890+0900")
@StaticMetamodel(GroupNotification.class)
public class GroupNotification_ {
	public static volatile SingularAttribute<GroupNotification, GroupNotificationPK> id;
	public static volatile SingularAttribute<GroupNotification, Timestamp> lastUpdatedDate;
	public static volatile SingularAttribute<GroupNotification, Integer> ver;
	public static volatile SingularAttribute<GroupNotification, Group> grp;
}
