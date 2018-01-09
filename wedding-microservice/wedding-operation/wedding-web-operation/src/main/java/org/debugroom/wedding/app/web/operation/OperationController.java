package org.debugroom.wedding.app.web.operation;

import java.util.List;

import javax.inject.Inject;

import org.modelmapper.ModelMapper;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import org.debugroom.wedding.domain.entity.operation.AuditLog;
import org.debugroom.wedding.domain.service.operation.LoggingService;

@RestController
@RequestMapping("/api/v1")
public class OperationController {

	@Inject
	ModelMapper mapper;
	
	@Inject
	LoggingService loggingService;
	
	@RequestMapping(method=RequestMethod.POST, value="/log/audit/new")
	public void saveAuditLog(
			@RequestBody org.debugroom.wedding.app.model.AuditLog auditLog){
		loggingService.saveAuditLog(mapper.map(auditLog, AuditLog.class));
	}
	
}
