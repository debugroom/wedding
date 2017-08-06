package org.debugroom.wedding.domain.entity.gallery;

import java.sql.Timestamp;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2017-07-31T01:09:20.911+0900")
@StaticMetamodel(PhotoRelatedFolder.class)
public class PhotoRelatedFolder_ {
	public static volatile SingularAttribute<PhotoRelatedFolder, PhotoRelatedFolderPK> id;
	public static volatile SingularAttribute<PhotoRelatedFolder, Timestamp> lastUpdatedDate;
	public static volatile SingularAttribute<PhotoRelatedFolder, Integer> ver;
	public static volatile SingularAttribute<PhotoRelatedFolder, Folder> folder;
	public static volatile SingularAttribute<PhotoRelatedFolder, Photo> photo;
}
