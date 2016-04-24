package org.debugroom.wedding.domain.model.entity;

import java.sql.Timestamp;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2016-04-17T19:24:01.741+0900")
@StaticMetamodel(Function.class)
public class Function_ {
	public static volatile SingularAttribute<Function, FunctionPK> id;
	public static volatile SingularAttribute<Function, Integer> authorityLevel;
	public static volatile SingularAttribute<Function, String> functionName;
	public static volatile SingularAttribute<Function, Timestamp> lastUpdatedDate;
	public static volatile SingularAttribute<Function, String> url;
	public static volatile SingularAttribute<Function, Date> usableEndDate;
	public static volatile SingularAttribute<Function, Date> usableStartDate;
	public static volatile SingularAttribute<Function, Integer> ver;
	public static volatile SingularAttribute<Function, Menu> menu;
}
