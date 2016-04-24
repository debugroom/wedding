package org.debugroom.wedding.domain.model.entity;

import java.sql.Timestamp;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2016-04-17T19:24:01.755+0900")
@StaticMetamodel(Notification.class)
public class Notification_ {
	public static volatile SingularAttribute<Notification, NotificationPK> id;
	public static volatile SingularAttribute<Notification, Boolean> isAccessed;
	public static volatile SingularAttribute<Notification, Timestamp> lastUpdatedDate;
	public static volatile SingularAttribute<Notification, Integer> ver;
	public static volatile SingularAttribute<Notification, Infomation> infomation;
	public static volatile SingularAttribute<Notification, User> usr;
}
