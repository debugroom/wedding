package org.debugroom.wedding.app.web;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class EnvProperties {

	@Value("${info.root.path}")
	private String infoRootPath;

}
