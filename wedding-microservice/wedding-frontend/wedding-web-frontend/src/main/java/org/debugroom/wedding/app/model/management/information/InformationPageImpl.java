package org.debugroom.wedding.app.model.management.information;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import org.debugroom.wedding.domain.entity.Information;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

public class InformationPageImpl extends PageImpl<Information>{

	private static final long serialVersionUID = -1849507035543002091L;

	@JsonCreator
	public InformationPageImpl(
			@JsonProperty("content") List<Information> content,
			@JsonProperty("number") int number,
			@JsonProperty("size") int size,
			@JsonProperty("totalElements") Long totalElements) {
		super(content, new PageRequest(number, size), totalElements);
	}

}
