package org.debugroom.wedding.app.web.galley;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class Photo implements Serializable{

	private static final long serialVersionUID = 515787842551845314L;

	public Photo(){
	}

	@NotNull
	@Size(min=10, max=10)
	@Pattern(regexp = "[0-9]*")
	private String photoId;
	
}
