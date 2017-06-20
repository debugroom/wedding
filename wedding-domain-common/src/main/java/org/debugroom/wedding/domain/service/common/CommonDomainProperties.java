package org.debugroom.wedding.domain.service.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class CommonDomainProperties {

	@Value("${credential.type.password}")
	private String credentialTypePassword;

	@Value("${password.expired.day.default}")
	private String passwordExpiredDayDefault;

}
