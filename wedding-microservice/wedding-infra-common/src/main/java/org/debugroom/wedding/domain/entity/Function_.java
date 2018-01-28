package org.debugroom.wedding.domain.entity;

import java.sql.Timestamp;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2017-07-20T00:12:35.858+0900")
@StaticMetamodel(Function.class)
public class Function_ {
	public static volatile SingularAttribute<Function, FunctionPK> id;
	public static volatile SingularAttribute<Function, Integer> authorityLevel;
	public static volatile SingularAttribute<Function, String> functionName;
	public static volatile SingularAttribute<Function, Timestamp> lastUpdatedDate;
	public static volatile SingularAttribute<Function, String> url;
	public static volatile SingularAttribute<Function, Boolean> hasPathvariables;
	public static volatile SingularAttribute<Function, Date> usableEndDate;
	public static volatile SingularAttribute<Function, Date> usableStartDate;
	public static volatile SingularAttribute<Function, Integer> ver;
	public static volatile SingularAttribute<Function, Menu> menu;
}
