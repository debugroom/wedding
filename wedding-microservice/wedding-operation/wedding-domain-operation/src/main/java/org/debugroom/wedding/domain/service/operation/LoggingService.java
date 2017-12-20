package org.debugroom.wedding.domain.service.operation;

import org.debugroom.wedding.domain.entity.operation.AuditLog;

public interface LoggingService {

	public void saveAuditLog(AuditLog auditLog);

}
