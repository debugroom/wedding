package org.debugroom.wedding.app.model.management.information;

import java.io.Serializable;

import org.debugroom.wedding.domain.entity.Information;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class InformationResource implements Serializable {

	private static final long serialVersionUID = -7262635603443854966L;
	private Information information;
	private String messageBodyUrl;
	
}
