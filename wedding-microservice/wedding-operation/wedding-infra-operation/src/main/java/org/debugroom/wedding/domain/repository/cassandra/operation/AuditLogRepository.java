package org.debugroom.wedding.domain.repository.cassandra.operation;

import org.springframework.data.repository.CrudRepository;

import org.debugroom.wedding.domain.entity.operation.AuditLog;
import org.debugroom.wedding.domain.entity.operation.LogPK;

public interface AuditLogRepository extends CrudRepository<AuditLog, LogPK>{

}
