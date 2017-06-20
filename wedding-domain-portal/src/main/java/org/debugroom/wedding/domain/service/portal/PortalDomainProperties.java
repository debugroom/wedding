package org.debugroom.wedding.domain.service.portal;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class PortalDomainProperties {

	@Value("${info.rootpath}")
	private String infoRootPath;

}
