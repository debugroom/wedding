package org.debugroom.wedding.domain.entity;

import java.sql.Timestamp;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2017-07-20T00:12:35.863+0900")
@StaticMetamodel(Information.class)
public class Information_ {
	public static volatile SingularAttribute<Information, String> infoId;
	public static volatile SingularAttribute<Information, String> infoPagePath;
	public static volatile SingularAttribute<Information, Timestamp> lastUpdatedDate;
	public static volatile SingularAttribute<Information, Timestamp> registratedDate;
	public static volatile SingularAttribute<Information, Timestamp> releaseDate;
	public static volatile SingularAttribute<Information, String> title;
	public static volatile SingularAttribute<Information, Integer> ver;
	public static volatile SetAttribute<Information, Notification> notifications;
}
