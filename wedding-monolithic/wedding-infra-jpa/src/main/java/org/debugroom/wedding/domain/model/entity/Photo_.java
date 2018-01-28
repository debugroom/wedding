package org.debugroom.wedding.domain.model.entity;

import java.sql.Timestamp;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2016-05-02T02:19:55.068+0900")
@StaticMetamodel(Photo.class)
public class Photo_ {
	public static volatile SingularAttribute<Photo, String> photoId;
	public static volatile SingularAttribute<Photo, String> filePath;
	public static volatile SingularAttribute<Photo, Boolean> isControled;
	public static volatile SingularAttribute<Photo, Timestamp> lastUpdatedDate;
	public static volatile SingularAttribute<Photo, String> thumbnailFilePath;
	public static volatile SingularAttribute<Photo, Integer> ver;
	public static volatile SetAttribute<Photo, GroupVisiblePhoto> groupVisiblePhotos;
	public static volatile SetAttribute<Photo, Message> messages;
	public static volatile SetAttribute<Photo, Group> grps;
	public static volatile SetAttribute<Photo, PhotoRelatedFolder> photoRelatedFolders;
	public static volatile SetAttribute<Photo, PhotoRelatedUser> photoRelatedUsers;
}
