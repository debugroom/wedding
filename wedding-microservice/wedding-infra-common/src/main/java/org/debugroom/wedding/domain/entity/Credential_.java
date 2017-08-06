package org.debugroom.wedding.domain.entity;

import java.sql.Timestamp;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2017-07-20T00:12:35.841+0900")
@StaticMetamodel(Credential.class)
public class Credential_ {
	public static volatile SingularAttribute<Credential, CredentialPK> id;
	public static volatile SingularAttribute<Credential, String> credentialKey;
	public static volatile SingularAttribute<Credential, Timestamp> lastUpdatedDate;
	public static volatile SingularAttribute<Credential, Timestamp> validDate;
	public static volatile SingularAttribute<Credential, Integer> ver;
	public static volatile SingularAttribute<Credential, User> usr;
}
