package org.debugroom.wedding.domain.model.entity;

import java.sql.Timestamp;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2016-05-01T03:03:07.403+0900")
@StaticMetamodel(RequestStatus.class)
public class RequestStatus_ {
	public static volatile SingularAttribute<RequestStatus, RequestStatusPK> id;
	public static volatile SingularAttribute<RequestStatus, Boolean> isAnswered;
	public static volatile SingularAttribute<RequestStatus, Timestamp> lastUpdatedDate;
	public static volatile SingularAttribute<RequestStatus, String> response;
	public static volatile SingularAttribute<RequestStatus, Integer> ver;
	public static volatile SingularAttribute<RequestStatus, Request> request;
	public static volatile SingularAttribute<RequestStatus, User> usr;
	public static volatile SingularAttribute<RequestStatus, Boolean> isApproved;
}
