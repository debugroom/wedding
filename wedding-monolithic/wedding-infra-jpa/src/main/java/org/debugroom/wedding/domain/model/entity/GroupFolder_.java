package org.debugroom.wedding.domain.model.entity;

import java.sql.Timestamp;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2016-05-02T02:07:04.665+0900")
@StaticMetamodel(GroupFolder.class)
public class GroupFolder_ {
	public static volatile SingularAttribute<GroupFolder, GroupFolderPK> id;
	public static volatile SingularAttribute<GroupFolder, Timestamp> lastUpdatedDate;
	public static volatile SingularAttribute<GroupFolder, Integer> ver;
	public static volatile SingularAttribute<GroupFolder, Folder> folder;
	public static volatile SingularAttribute<GroupFolder, Group> grp;
}
