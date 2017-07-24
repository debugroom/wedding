package org.debugroom.wedding.domain.entity;

import java.sql.Timestamp;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2017-07-20T00:12:35.845+0900")
@StaticMetamodel(Email.class)
public class Email_ {
	public static volatile SingularAttribute<Email, EmailPK> id;
	public static volatile SingularAttribute<Email, String> email;
	public static volatile SingularAttribute<Email, Timestamp> lastUpdatedDate;
	public static volatile SingularAttribute<Email, Integer> ver;
	public static volatile SingularAttribute<Email, User> usr;
}
