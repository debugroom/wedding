package org.debugroom.wedding.domain.entity.management;

import java.sql.Timestamp;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2017-07-29T16:02:32.173+0900")
@StaticMetamodel(Request.class)
public class Request_ {
	public static volatile SingularAttribute<Request, String> requestId;
	public static volatile SingularAttribute<Request, Timestamp> lastUpdatedDate;
	public static volatile SingularAttribute<Request, Timestamp> registratedDate;
	public static volatile SingularAttribute<Request, String> requestContents;
	public static volatile SingularAttribute<Request, String> title;
	public static volatile SingularAttribute<Request, Integer> ver;
	public static volatile SetAttribute<Request, RequestStatus> requestStatuses;
}
