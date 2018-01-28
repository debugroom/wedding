package org.debugroom.wedding.domain.entity;

import java.sql.Timestamp;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2017-12-16T19:24:57.303+0900")
@StaticMetamodel(Menu.class)
public class Menu_ {
	public static volatile SingularAttribute<Menu, String> menuId;
	public static volatile SingularAttribute<Menu, Integer> authorityLevel;
	public static volatile SingularAttribute<Menu, Timestamp> lastUpdatedDate;
	public static volatile SingularAttribute<Menu, String> menuName;
	public static volatile SingularAttribute<Menu, String> url;
	public static volatile SingularAttribute<Menu, Boolean> hasPathvariables;
	public static volatile SingularAttribute<Menu, Timestamp> usableEndDate;
	public static volatile SingularAttribute<Menu, Timestamp> usableStartDate;
	public static volatile SingularAttribute<Menu, Integer> ver;
	public static volatile SetAttribute<Menu, Function> fnctions;
}
