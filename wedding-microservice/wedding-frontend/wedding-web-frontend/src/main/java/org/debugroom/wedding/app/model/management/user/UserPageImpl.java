package org.debugroom.wedding.app.model.management.user;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import org.debugroom.wedding.app.model.User;

public class UserPageImpl extends PageImpl<User>{

	private static final long serialVersionUID = -1116257878995682210L;

	@JsonCreator
	public UserPageImpl(
			@JsonProperty("content") List<User> content,
            @JsonProperty("number") int number,
            @JsonProperty("size") int size,
            @JsonProperty("totalElements") Long totalElements){
		super(content, new PageRequest(number, size), totalElements);
	}

}
