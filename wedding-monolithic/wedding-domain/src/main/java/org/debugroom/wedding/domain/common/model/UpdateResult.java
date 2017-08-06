package org.debugroom.wedding.domain.common.model;

import java.util.List;

import lombok.Data;

@Data
public class UpdateResult<E> {
	
	private List<String> updateParamList;
	private E beforeEntity;
	private E afterEntity;

}
