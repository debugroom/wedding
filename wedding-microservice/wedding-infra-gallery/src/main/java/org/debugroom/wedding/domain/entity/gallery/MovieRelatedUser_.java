package org.debugroom.wedding.domain.entity.gallery;

import java.sql.Timestamp;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2017-07-31T01:09:20.906+0900")
@StaticMetamodel(MovieRelatedUser.class)
public class MovieRelatedUser_ {
	public static volatile SingularAttribute<MovieRelatedUser, MovieRelatedUserPK> id;
	public static volatile SingularAttribute<MovieRelatedUser, Timestamp> lastUpdatedDate;
	public static volatile SingularAttribute<MovieRelatedUser, Integer> ver;
	public static volatile SingularAttribute<MovieRelatedUser, Movie> movie;
	public static volatile SingularAttribute<MovieRelatedUser, User> usr;
}
