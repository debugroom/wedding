package org.debugroom.wedding.app.web.interceptor;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Collections;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.debugroom.wedding.app.model.AuditLog;
import org.slf4j.MDC;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;

public abstract class AbstractAuditLoggingInterceptor extends HandlerInterceptorAdapter{

	private static final String MASK_STRING = "**********";
	private static final String MDC_KEY_NAME = "X-Track";
	
	protected String createJsonRepresentationHeaderValue(
			HttpServletRequest request) throws IOException{
		//HTTPヘッダをJSON形式に変換する。
		JsonFactory jsonFactory = new JsonFactory();
		StringWriter stringWriter = new StringWriter();
		JsonGenerator jsonGenerator = jsonFactory.createGenerator(stringWriter);
		jsonGenerator.writeStartObject();
		for(String headerName : Collections.list(request.getHeaderNames())){
			jsonGenerator.writeStringField(headerName, request.getHeader(headerName));
		}
		jsonGenerator.writeEndObject();
		jsonGenerator.close();
		return stringWriter.toString();
	}

	protected String createJsonRepresentationRequestParameters(
			HttpServletRequest request) throws IOException{
		//リクエストパラメータをJSON形式に変換する。
		JsonFactory jsonFactory = new JsonFactory();
		StringWriter stringWriter = new StringWriter();
		JsonGenerator jsonGenerator = jsonFactory.createGenerator(stringWriter);
		jsonGenerator.writeStartObject();
		for(String requestParamName : Collections.list(request.getParameterNames())){
			// パスワード関連のリクエストパラメータはマスク化する。
			if("password".equals(requestParamName)){
				jsonGenerator.writeStringField(requestParamName, MASK_STRING);
			}else if(StringUtils.startsWith(requestParamName, "credentials")){
				jsonGenerator.writeStringField(requestParamName, MASK_STRING);
			}else{
				jsonGenerator.writeStringField(requestParamName,
					request.getHeader(requestParamName));
			}
		}
		jsonGenerator.writeEndObject();
		jsonGenerator.close();
		return stringWriter.toString();	
	}
	
	protected AuditLog createAuditLogDraft(HttpServletRequest request) 
			throws IllegalArgumentException, IOException{
		//リバースプロキシで置き換えられるクライアントのIPアドレスを設定する。
		String requestIpAddress = request.getRemoteAddr();
		if(null != request.getParameter("X-Forwarded-For")){
			requestIpAddress = StringUtils.substringBefore(
					request.getParameter("X-Forwarded-For"), ",");
		}
		String sessionId = "";
		if(null != request.getSession(false)){
			sessionId = request.getSession(false).getId();
		}
		return AuditLog.builder()
				.hostIpAddress(request.getLocalAddr())
				.trackId(MDC.get(MDC_KEY_NAME))
				.sessionId(sessionId)
				.requestIpAddress(requestIpAddress)
				.userAgent(request.getHeader("user-agent"))
				.referer(request.getHeader("referer"))
				.headerDump(createJsonRepresentationHeaderValue(request))
				.cookie(request.getHeader("cookie"))
				.parameters(createJsonRepresentationRequestParameters(request))
				.build();
	}

}
