package org.debugroom.wedding.domain.model.entity;

import java.sql.Timestamp;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2016-05-02T02:07:04.673+0900")
@StaticMetamodel(GroupVisiblePhoto.class)
public class GroupVisiblePhoto_ {
	public static volatile SingularAttribute<GroupVisiblePhoto, GroupVisiblePhotoPK> id;
	public static volatile SingularAttribute<GroupVisiblePhoto, Timestamp> lastUpdatedDate;
	public static volatile SingularAttribute<GroupVisiblePhoto, Integer> ver;
	public static volatile SingularAttribute<GroupVisiblePhoto, Group> grp;
	public static volatile SingularAttribute<GroupVisiblePhoto, Photo> photo;
}
