package org.debugroom.wedding.domain.entity.gallery;

import java.sql.Timestamp;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2017-07-31T01:09:20.915+0900")
@StaticMetamodel(PhotoRelatedUser.class)
public class PhotoRelatedUser_ {
	public static volatile SingularAttribute<PhotoRelatedUser, PhotoRelatedUserPK> id;
	public static volatile SingularAttribute<PhotoRelatedUser, Timestamp> lastUpdatedDate;
	public static volatile SingularAttribute<PhotoRelatedUser, Integer> ver;
	public static volatile SingularAttribute<PhotoRelatedUser, Photo> photo;
	public static volatile SingularAttribute<PhotoRelatedUser, User> usr;
}
