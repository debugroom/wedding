package org.debugroom.wedding.domain.model.entity;

import java.sql.Timestamp;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2016-05-02T02:07:04.681+0900")
@StaticMetamodel(MovieRelatedFolder.class)
public class MovieRelatedFolder_ {
	public static volatile SingularAttribute<MovieRelatedFolder, MovieRelatedFolderPK> id;
	public static volatile SingularAttribute<MovieRelatedFolder, Timestamp> lastUpdatedDate;
	public static volatile SingularAttribute<MovieRelatedFolder, Integer> ver;
	public static volatile SingularAttribute<MovieRelatedFolder, Folder> folder;
	public static volatile SingularAttribute<MovieRelatedFolder, Movie> movie;
}
