package org.debugroom.wedding.domain.entity.gallery;

import java.sql.Timestamp;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2017-07-31T01:09:20.909+0900")
@StaticMetamodel(Photo.class)
public class Photo_ {
	public static volatile SingularAttribute<Photo, String> photoId;
	public static volatile SingularAttribute<Photo, String> filePath;
	public static volatile SingularAttribute<Photo, Boolean> isControled;
	public static volatile SingularAttribute<Photo, Timestamp> lastUpdatedDate;
	public static volatile SingularAttribute<Photo, String> thumbnailFilePath;
	public static volatile SingularAttribute<Photo, Integer> ver;
	public static volatile SetAttribute<Photo, GroupVisiblePhoto> groupVisiblePhotos;
	public static volatile SetAttribute<Photo, PhotoRelatedFolder> photoRelatedFolders;
	public static volatile SetAttribute<Photo, PhotoRelatedUser> photoRelatedUsers;
}
