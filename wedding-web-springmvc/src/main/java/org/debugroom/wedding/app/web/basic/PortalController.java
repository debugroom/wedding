package org.debugroom.wedding.app.web.basic;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import org.debugroom.wedding.domain.service.basic.PortalService;

/**
 * 
 * @author org.debugroom
 *
 */
@Controller
public class PortalController {

	@Inject
	PortalService service;
	
    @RequestMapping(method = RequestMethod.GET, value="/portal")
    public String portal(){
        return "basic/portal";
    }

    @RequestMapping(method = RequestMethod.GET, value="/sample")
    public String sample(){
        return "basic/sample";
    }
}
