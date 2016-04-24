package org.debugroom.wedding.domain.model.entity;

import java.sql.Timestamp;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2016-04-17T19:24:01.754+0900")
@StaticMetamodel(Movie.class)
public class Movie_ {
	public static volatile SingularAttribute<Movie, String> movieId;
	public static volatile SingularAttribute<Movie, String> filePath;
	public static volatile SingularAttribute<Movie, Boolean> isControled;
	public static volatile SingularAttribute<Movie, Timestamp> lastUpdatedDate;
	public static volatile SingularAttribute<Movie, Integer> ver;
	public static volatile SetAttribute<Movie, GroupVisibleMovie> groupVisibleMovies;
	public static volatile SetAttribute<Movie, Message> messages;
	public static volatile SetAttribute<Movie, Group> grps;
}
