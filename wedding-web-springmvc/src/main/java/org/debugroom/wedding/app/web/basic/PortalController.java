package org.debugroom.wedding.app.web.basic;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.debugroom.framework.common.exception.BusinessException;
import org.debugroom.wedding.domain.basic.service.PortalService;
import org.debugroom.wedding.domain.model.entity.User;

/**
 * 
 * @author org.debugroom
 *
 */
@Controller
public class PortalController {

	@Inject
	PortalService portalService;
	
    @RequestMapping(method = RequestMethod.GET, value="/portal")
    public String portal(Model model){
    	//TODO Spring Securityの認証ロジック後にリダイレクトする。
    	try {
			model.addAttribute(portalService.getPortalInfo(
					User.builder().userId("00000001").build()));
		} catch (BusinessException e) {
			e.printStackTrace();
		}
        return "basic/portal";
    }

    @RequestMapping(method = RequestMethod.GET, value="/infomation/{infoId}")
    public String infomation(@Validated Infomation infomation,
    		BindingResult bindingResult, Model model){
    	if(bindingResult.hasErrors()){
    		return portal(model);
    	}
    	model.addAttribute(portalService.getInfomation(infomation.getInfoId()));
    	return "basic/infomation";
    }

    @RequestMapping(method = RequestMethod.GET, value="/request/{requestId}")
    public String request(@Validated Request request,
    		BindingResult bindingResult, Model model){
    	if(bindingResult.hasErrors()){
    		return portal(model);
    	}
    	model.addAttribute(portalService.getRequest(request.getRequestId()));
    	return "basic/request";
    }

    @RequestMapping(method = RequestMethod.GET, value="/sample")
    public String sample(){
        return "basic/sample";
    }
}
