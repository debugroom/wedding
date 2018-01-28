package org.debugroom.wedding.domain.service.management;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class ManagementDomainProperties {

	@Value("info.root.path")
	private String infoRootPath;
	
}
