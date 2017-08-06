package org.debugroom.wedding.domain.model.entity;

import java.sql.Timestamp;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2016-05-02T02:07:04.670+0900")
@StaticMetamodel(GroupVisibleMovie.class)
public class GroupVisibleMovie_ {
	public static volatile SingularAttribute<GroupVisibleMovie, GroupVisibleMoviePK> id;
	public static volatile SingularAttribute<GroupVisibleMovie, Timestamp> lastUpdatedDate;
	public static volatile SingularAttribute<GroupVisibleMovie, Integer> ver;
	public static volatile SingularAttribute<GroupVisibleMovie, Group> grp;
	public static volatile SingularAttribute<GroupVisibleMovie, Movie> movie;
}
