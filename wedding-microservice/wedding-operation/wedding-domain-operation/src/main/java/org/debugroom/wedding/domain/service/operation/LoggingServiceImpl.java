package org.debugroom.wedding.domain.service.operation;

import java.util.Date;

import javax.inject.Inject;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import org.debugroom.wedding.domain.entity.operation.AuditLog;
import org.debugroom.wedding.domain.repository.cassandra.operation.AuditLogRepository;

@Profile("operation")
@Service("loggingService")
public class LoggingServiceImpl implements LoggingService{

	@Inject
	AuditLogRepository auditLogRepository;
	
	@Override
	public void saveAuditLog(AuditLog auditLog) {
		auditLog.getLogpk().setTimeStamp(new Date());
		auditLog.setVer(0);
		auditLog.setLastUpdatedDate(new Date());
		auditLogRepository.save(auditLog);
	}

}
