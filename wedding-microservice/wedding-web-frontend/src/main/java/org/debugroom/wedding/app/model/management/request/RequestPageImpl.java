package org.debugroom.wedding.app.model.management.request;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

public class RequestPageImpl extends PageImpl<Request>{
	
	private static final long serialVersionUID = -4724170952373088945L;

	@JsonCreator
	public RequestPageImpl(
			@JsonProperty("content") List<Request> content,
			@JsonProperty("number") int number,
			@JsonProperty("size") int size,
			@JsonProperty("totalElements") Long totalElements){
		super(content, new PageRequest(number, size), totalElements);
	}

}
