package org.debugroom.wedding.external;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Data
public class ExternalProperties {

	@Value("${external.japanpost.address.search.api.host}")
	private String addressSearchAPIHost;
	
}
