package org.debugroom.wedding.domain.model.entity;

import java.sql.Timestamp;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2016-05-02T02:07:04.676+0900")
@StaticMetamodel(Infomation.class)
public class Infomation_ {
	public static volatile SingularAttribute<Infomation, String> infoId;
	public static volatile SingularAttribute<Infomation, String> infoPagePath;
	public static volatile SingularAttribute<Infomation, Timestamp> lastUpdatedDate;
	public static volatile SingularAttribute<Infomation, Timestamp> registratedDate;
	public static volatile SingularAttribute<Infomation, Timestamp> releaseDate;
	public static volatile SingularAttribute<Infomation, String> title;
	public static volatile SingularAttribute<Infomation, Integer> ver;
	public static volatile SetAttribute<Infomation, GroupNotification> groupNotifications;
	public static volatile SetAttribute<Infomation, Group> grps;
	public static volatile SetAttribute<Infomation, Notification> notifications;
}
