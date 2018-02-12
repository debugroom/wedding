package org.debugroom.wedding.app.model.aws.gallery;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class UploadFileForm implements Serializable{

	private static final long serialVersionUID = -8112626874155411044L;

	public UploadFileForm(){
	}
	
	@NotNull
	@Size(min=12, max=12)
	@Pattern(regexp = "[0-9]*")
	private String folderId;
	@Pattern(regexp="^[^=#$%&/<>?¥^¥~¥[¥]¥¥]+$")
	private String fileName;
	
	
}
