package org.debugroom.wedding.domain.model.entity;

import java.sql.Timestamp;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2016-05-02T02:07:04.679+0900")
@StaticMetamodel(MessageBoard.class)
public class MessageBoard_ {
	public static volatile SingularAttribute<MessageBoard, String> messageBoardId;
	public static volatile SingularAttribute<MessageBoard, Timestamp> lastUpdatedDate;
	public static volatile SingularAttribute<MessageBoard, String> title;
	public static volatile SingularAttribute<MessageBoard, Integer> ver;
	public static volatile SetAttribute<MessageBoard, Message> messages;
}
