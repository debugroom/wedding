package org.debugroom.wedding.domain.entity;

import java.sql.Timestamp;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2017-07-20T00:12:35.692+0900")
@StaticMetamodel(Address.class)
public class Address_ {
	public static volatile SingularAttribute<Address, String> userId;
	public static volatile SingularAttribute<Address, String> address;
	public static volatile SingularAttribute<Address, Timestamp> lastUpdatedDate;
	public static volatile SingularAttribute<Address, String> postCd;
	public static volatile SingularAttribute<Address, Integer> ver;
	public static volatile SingularAttribute<Address, User> usr;
}
