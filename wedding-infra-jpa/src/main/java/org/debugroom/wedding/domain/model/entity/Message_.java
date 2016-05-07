package org.debugroom.wedding.domain.model.entity;

import java.sql.Timestamp;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2016-05-02T02:07:04.678+0900")
@StaticMetamodel(Message.class)
public class Message_ {
	public static volatile SingularAttribute<Message, MessagePK> id;
	public static volatile SingularAttribute<Message, Timestamp> lastUpdatedDate;
	public static volatile SingularAttribute<Message, Integer> likeCount;
	public static volatile SingularAttribute<Message, String> message;
	public static volatile SingularAttribute<Message, Integer> ver;
	public static volatile SingularAttribute<Message, MessageBoard> messageBoard;
	public static volatile SingularAttribute<Message, Movie> movie;
	public static volatile SingularAttribute<Message, Photo> photo;
}
