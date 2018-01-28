package org.debugroom.wedding.app;

import java.util.List;

import org.debugroom.framework.common.exception.BusinessException;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper=false)
public class FileDownloadException extends BusinessException{
	private static final long serialVersionUID = -4472226383889656538L;
	private List<String> messages;
	
}
