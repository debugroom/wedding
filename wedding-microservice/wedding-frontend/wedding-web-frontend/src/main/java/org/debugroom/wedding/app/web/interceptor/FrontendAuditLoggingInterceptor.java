package org.debugroom.wedding.app.web.interceptor;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import org.debugroom.wedding.app.model.AuditLog;
import org.debugroom.wedding.app.model.LogPK;
import org.debugroom.wedding.app.web.helper.AuditLoggingHelper;
import org.debugroom.wedding.app.web.security.CustomUserDetails;
import org.debugroom.wedding.domain.service.common.DateUtil;
import org.jboss.logging.MDC;

@Component
public class FrontendAuditLoggingInterceptor extends AbstractAuditLoggingInterceptor {

	private static final String HEADER_USER_ID_KEY_NAME = "X-UserId";
	
	@Inject
	AuditLoggingHelper auditLoggingHelper;
	
	public FrontendAuditLoggingInterceptor(AuditLoggingHelper auditLoggingHelper) {
		this.auditLoggingHelper = auditLoggingHelper;
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String userId = "99999999";
		SecurityContext securityContext = SecurityContextHolder.getContext();
		Authentication authentication = securityContext.getAuthentication();
		if(null != authentication){
			Object principal = authentication.getPrincipal();
			if(principal instanceof CustomUserDetails){
				CustomUserDetails customUserDetails = (CustomUserDetails)principal;
				userId = customUserDetails.getUser().getUserId();
			}
		}
		MDC.put(HEADER_USER_ID_KEY_NAME, userId);
		return super.preHandle(request, response, handler);
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, 
			Object handler, ModelAndView modelAndView) throws Exception {
		// 監査ログオブジェクトを構築する。
		AuditLog auditLog = createAuditLogDraft(request);
		auditLog.setServiceName("frontend");
		SecurityContext securityContext = SecurityContextHolder.getContext();
		Authentication authentication = securityContext.getAuthentication();
		if(null != authentication){
			Object principal = authentication.getPrincipal();
			if(principal instanceof CustomUserDetails){
				CustomUserDetails customUserDetails = (CustomUserDetails)principal;
				LogPK logPK = LogPK.builder()
								.userId(customUserDetails.getUser().getUserId())
								.timeStamp(DateUtil.getCurrentDate())
								.build();
				auditLog.setLogPK(logPK);
			}
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
