package org.debugroom.wedding.domain.service.management;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.debugroom.framework.common.exception.BusinessException;
import org.debugroom.wedding.domain.model.entity.Infomation;

public interface InfomationManagementService {

	public Page<Infomation> getInfomationUsingPage(Pageable pageable);

	public InfomationDetail getInfomationDetail(String infoId);

}
