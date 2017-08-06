package org.debugroom.wedding.domain.model.entity;

import java.sql.Timestamp;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2016-05-02T02:07:04.656+0900")
@StaticMetamodel(Credential.class)
public class Credential_ {
	public static volatile SingularAttribute<Credential, CredentialPK> id;
	public static volatile SingularAttribute<Credential, String> credentialKey;
	public static volatile SingularAttribute<Credential, Timestamp> lastUpdatedDate;
	public static volatile SingularAttribute<Credential, Timestamp> validDate;
	public static volatile SingularAttribute<Credential, Integer> ver;
	public static volatile SingularAttribute<Credential, User> usr;
}
