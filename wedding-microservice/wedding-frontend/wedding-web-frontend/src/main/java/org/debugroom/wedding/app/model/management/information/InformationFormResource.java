package org.debugroom.wedding.app.model.management.information;

import java.io.Serializable;
import java.util.List;

import org.debugroom.wedding.domain.entity.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class InformationFormResource implements Serializable{

	private static final long serialVersionUID = 6588570197284303413L;

	public InformationFormResource(){
	}

	private List<User> users;

}
