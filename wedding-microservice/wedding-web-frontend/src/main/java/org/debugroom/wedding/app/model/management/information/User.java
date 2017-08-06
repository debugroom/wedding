package org.debugroom.wedding.app.model.management.information;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.debugroom.wedding.app.model.management.information.Information.UpdateInformation;
import org.debugroom.wedding.app.model.management.information.NewInformationForm.ConfirmInformation;
import org.debugroom.wedding.app.model.management.information.NewInformationForm.SaveInformation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class User implements Serializable{

	private static final long serialVersionUID = -3219928038897177284L;

	public User(){
	}
	
	@Size(min=8, max=8, 
		groups= {ConfirmInformation.class, SaveInformation.class, UpdateInformation.class})
	@Pattern(regexp= "[0-9]*", groups= {ConfirmInformation.class, SaveInformation.class})
	private String userId;
	
	@Size(min=1, max=50, 
		groups= {ConfirmInformation.class, SaveInformation.class, UpdateInformation.class})
	private String firstName;
	
	@Size(min=1, max=50, 
		groups= {ConfirmInformation.class, SaveInformation.class, UpdateInformation.class})
	private String lastName;
	
}
