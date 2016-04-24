package org.debugroom.wedding.domain.model.entity;

import java.sql.Timestamp;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2016-04-17T19:24:01.736+0900")
@StaticMetamodel(Affiliation.class)
public class Affiliation_ {
	public static volatile SingularAttribute<Affiliation, AffiliationPK> id;
	public static volatile SingularAttribute<Affiliation, Timestamp> lastUpdatedDate;
	public static volatile SingularAttribute<Affiliation, Integer> ver;
	public static volatile SingularAttribute<Affiliation, Group> grp;
	public static volatile SingularAttribute<Affiliation, User> usr;
}
