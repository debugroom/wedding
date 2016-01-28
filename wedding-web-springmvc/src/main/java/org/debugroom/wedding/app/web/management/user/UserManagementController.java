package org.debugroom.wedding.app.web.management.user;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.dozer.Mapper;
import org.dozer.MappingException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.debugroom.wedding.domain.model.entity.User;
import org.debugroom.wedding.domain.service.management.UserManagementService;

/**
 * 
 * @author org.debugroom
 *
 */
@Controller
public class UserManagementController {

	@Inject
	UserManagementService userManagementService;
	
	@RequestMapping(method = RequestMethod.GET, value="/management/user/portal")
	public String userManagementPortal(Pageable pageable, Model model){
		model.addAttribute("page", userManagementService.getUsersUsingPage(pageable));
		return "management/user/portal";
	}

}
