package org.debugroom.wedding.domain.model.common;

import java.util.List;

import lombok.Data;

@Data
public class UpdateResult<E> {

	private List<String> updateParamList;
	private E beforeEntity;
	private E afterEntity;

}
