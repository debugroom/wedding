package org.debugroom.wedding.app.handler.gallery;

import org.springframework.cloud.function.adapter.aws.SpringBootRequestHandler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.s3.event.S3EventNotification;

public class S3UploadEventHanlder extends
    SpringBootRequestHandler<S3EventNotification, String>{

	public S3UploadEventHanlder(){
		super();
	}
	
	public S3UploadEventHanlder(Class<?> configurationClass){
		super(configurationClass);
	}
	
	@Override
	public Object handleRequest(S3EventNotification event, Context context){
		return super.handleRequest(event, context);
	}

}
