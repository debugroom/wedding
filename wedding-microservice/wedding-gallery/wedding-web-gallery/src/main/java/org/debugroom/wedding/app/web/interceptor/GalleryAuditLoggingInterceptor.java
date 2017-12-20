package org.debugroom.wedding.app.web.interceptor;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import org.debugroom.wedding.app.model.AuditLog;
import org.debugroom.wedding.app.model.LogPK;
import org.debugroom.wedding.app.web.helper.AuditLoggingHelper;
import org.debugroom.wedding.domain.service.common.DateUtil;

@Component
public class GalleryAuditLoggingInterceptor extends AbstractAuditLoggingInterceptor{

	private static final String HEADER_USER_ID_KEY_NAME = "X-UserId";

	@Inject
	AuditLoggingHelper auditLoggingHelper;
	
	public GalleryAuditLoggingInterceptor(AuditLoggingHelper auditLoggingHelper){
		this.auditLoggingHelper = auditLoggingHelper;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, 
			Object handler, ModelAndView modelAndView) throws Exception {
		AuditLog auditLog = createAuditLogDraft(request);
		auditLog.setServiceName("gallery");
		auditLog.setCookie("");
		auditLog.setReferer("");
		String userId = MDC.get(HEADER_USER_ID_KEY_NAME);
		if(null != userId || !"".equals(userId)){
			LogPK logPK = LogPK.builder()
					.userId(userId)
					.timeStamp(DateUtil.getCurrentDate())
					.build();
			auditLog.setLogPK(logPK);
		}
		if(null == auditLog.getLogPK()){
			LogPK logPK = LogPK.builder()
					.userId("99999999")
					.timeStamp(DateUtil.getCurrentDate())
					.build();
			auditLog.setLogPK(logPK);
		}
		if(null != modelAndView){
			auditLog.setViewName(modelAndView.getViewName());
		}else{
			auditLog.setViewName(handler.toString());
		}
		auditLoggingHelper.saveAuditLog(auditLog);
	}

}
