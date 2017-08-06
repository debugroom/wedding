package org.debugroom.wedding.domain.model.entity;

import java.sql.Timestamp;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2016-05-02T02:07:04.660+0900")
@StaticMetamodel(Folder.class)
public class Folder_ {
	public static volatile SingularAttribute<Folder, String> folderId;
	public static volatile SingularAttribute<Folder, String> folderName;
	public static volatile SingularAttribute<Folder, Timestamp> lastUpdatedDate;
	public static volatile SingularAttribute<Folder, String> parentFolderId;
	public static volatile SingularAttribute<Folder, Integer> ver;
	public static volatile SingularAttribute<Folder, User> usr;
	public static volatile SetAttribute<Folder, GroupFolder> groupFolders;
	public static volatile SetAttribute<Folder, MovieRelatedFolder> movieRelatedFolders;
	public static volatile SetAttribute<Folder, PhotoRelatedFolder> photoRelatedFolders;
	public static volatile SetAttribute<Folder, UserRelatedFolder> userRelatedFolders;
}
