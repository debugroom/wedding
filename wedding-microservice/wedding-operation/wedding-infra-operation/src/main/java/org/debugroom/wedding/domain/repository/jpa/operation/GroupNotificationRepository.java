package org.debugroom.wedding.domain.repository.jpa.operation;

import org.debugroom.wedding.domain.entity.operation.GroupNotification;
import org.debugroom.wedding.domain.entity.operation.GroupNotificationPK;

import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupNotificationRepository  
	extends JpaRepository<GroupNotification, GroupNotificationPK>{

}
