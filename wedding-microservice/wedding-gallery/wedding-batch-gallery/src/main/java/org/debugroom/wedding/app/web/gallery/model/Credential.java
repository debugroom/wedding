package org.debugroom.wedding.app.web.gallery.model;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class Credential implements Serializable{

	private static final long serialVersionUID = 1986570797244027895L;

	public Credential(){
	}

	@NotNull
	@Pattern(regexp="^[^ =#$%&./<>?¥^¥~¥[¥]¥(¥)¥¥]+$")
	private String accessKey;
	
}
