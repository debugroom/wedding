package org.debugroom.wedding.app.web.management.infomation;

import java.util.Set;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;
import javax.inject.Named;

import org.dozer.Mapper;
import org.dozer.MappingException;

import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.debugroom.wedding.app.web.management.infomation.InfomationDatailForm.GetInfomation;
import org.debugroom.wedding.domain.service.management.InfomationManagementService;

/**
 * 
 * @author org.debugroom
 *
 */
@Controller
public class InfomationManagementController {

	@ModelAttribute
	public InfomationDatailForm setUpInfomationDetailForm(){
		return InfomationDatailForm.builder().build();
	}

	@Inject
	InfomationManagementService infomationManagementService;
	
	@RequestMapping(method = RequestMethod.GET, value="/management/infomation/portal")
	public String infomationManagementPortal(@PageableDefault(
												page=0,
												size=10,
												direction = Direction.ASC,
												sort = {"infoId"}
												) Pageable pageable, Model model){
		model.addAttribute("page", infomationManagementService
				.getInfomationUsingPage(pageable));
		return "management/infomation/portal";
	}
	
	@RequestMapping(method = RequestMethod.GET, value="/management/infomation/{infoId}")
	public String getInfomation(@Validated(GetInfomation.class) 
								InfomationDatailForm infomationDetailForm,
								BindingResult bindingResult, @PageableDefault(
										page=0,
										size=10,
										direction = Direction.ASC,
										sort = {"infoId"}
										) Pageable pageable, Model model){
		
		if(bindingResult.hasErrors()){
			model.addAttribute("page", infomationManagementService
				.getInfomationUsingPage(pageable));
			return "management/infomation/portal";
		}
		
		model.addAttribute(infomationManagementService
				.getInfomationDetail(infomationDetailForm.getInfoId()));

		return "management/infomation/detail";
	}

}
