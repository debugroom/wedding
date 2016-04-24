package org.debugroom.wedding.domain.model.entity;

import java.sql.Timestamp;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2016-04-17T19:24:01.743+0900")
@StaticMetamodel(Group.class)
public class Group_ {
	public static volatile SingularAttribute<Group, String> groupId;
	public static volatile SingularAttribute<Group, String> groupName;
	public static volatile SingularAttribute<Group, Timestamp> lastUpdatedDate;
	public static volatile SingularAttribute<Group, Integer> ver;
	public static volatile SetAttribute<Group, Affiliation> affiliations;
	public static volatile SetAttribute<Group, GroupNotification> groupNotifications;
	public static volatile SetAttribute<Group, GroupVisibleMovie> groupVisibleMovies;
	public static volatile SetAttribute<Group, GroupVisiblePhoto> groupVisiblePhotos;
	public static volatile SetAttribute<Group, Infomation> infomations;
	public static volatile SetAttribute<Group, Movie> movies;
	public static volatile SetAttribute<Group, Photo> photos;
	public static volatile SetAttribute<Group, User> usrs;
}
