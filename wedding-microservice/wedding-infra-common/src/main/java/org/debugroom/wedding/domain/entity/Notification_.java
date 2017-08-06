package org.debugroom.wedding.domain.entity;

import java.sql.Timestamp;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2017-07-20T00:12:35.865+0900")
@StaticMetamodel(Notification.class)
public class Notification_ {
	public static volatile SingularAttribute<Notification, NotificationPK> id;
	public static volatile SingularAttribute<Notification, Boolean> isAccessed;
	public static volatile SingularAttribute<Notification, Timestamp> lastUpdatedDate;
	public static volatile SingularAttribute<Notification, Integer> ver;
	public static volatile SingularAttribute<Notification, Information> information;
	public static volatile SingularAttribute<Notification, User> usr;
}
