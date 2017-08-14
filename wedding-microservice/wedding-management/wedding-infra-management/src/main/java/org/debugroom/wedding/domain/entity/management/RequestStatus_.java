package org.debugroom.wedding.domain.entity.management;

import java.sql.Timestamp;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2017-07-29T16:02:32.175+0900")
@StaticMetamodel(RequestStatus.class)
public class RequestStatus_ {
	public static volatile SingularAttribute<RequestStatus, RequestStatusPK> id;
	public static volatile SingularAttribute<RequestStatus, Boolean> isAnswered;
	public static volatile SingularAttribute<RequestStatus, Boolean> isApproved;
	public static volatile SingularAttribute<RequestStatus, Timestamp> lastUpdatedDate;
	public static volatile SingularAttribute<RequestStatus, String> response;
	public static volatile SingularAttribute<RequestStatus, Integer> ver;
	public static volatile SingularAttribute<RequestStatus, Request> request;
	public static volatile SingularAttribute<RequestStatus, User> usr;
}
