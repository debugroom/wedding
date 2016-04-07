package org.debugroom.wedding.domain.service.management;

import java.io.Serializable;
import java.util.List;

import org.debugroom.wedding.domain.model.entity.User;
import org.debugroom.wedding.domain.model.entity.Infomation;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Data
@AllArgsConstructor
@Builder
public class InfomationDraft implements Serializable{

	private static final long serialVersionUID = -3692704873197819670L;

	public InfomationDraft(){
	}

	private Infomation infomation;
	private String infoName;
	private String messageBody;
	private List<User> viewUsers;

}
