package org.debugroom.wedding.common.external.api;

import org.debugroom.framework.common.exception.BusinessException;

import org.debugroom.wedding.common.external.model.Address;

public interface AddressSearch {

	public Address getAddress(String zipCode) throws BusinessException;

}
