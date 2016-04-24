package org.debugroom.wedding.app.web.management.infomation;

import java.util.Set;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletContext;

import org.dozer.Mapper;
import org.dozer.MappingException;
import org.springframework.beans.propertyeditors.CustomDateEditor;
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
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.debugroom.framework.common.exception.BusinessException;
import org.debugroom.wedding.app.web.common.model.UserSearchCriteria;
import org.debugroom.wedding.app.web.common.model.UserSearchResult;
import org.debugroom.wedding.app.web.management.infomation.InfomationDatailForm.GetInfomation;
import org.debugroom.wedding.app.web.management.infomation.NewInfomationForm.ConfirmInfomation;
import org.debugroom.wedding.app.web.management.infomation.NewInfomationForm.SaveInfomation;
import org.debugroom.wedding.domain.service.management.InfomationManagementService;
import org.debugroom.wedding.domain.service.management.InfomationDetail;
import org.debugroom.wedding.domain.service.management.InfomationDraft;
import org.debugroom.wedding.domain.model.entity.Infomation;

/**
 * 
 * @author org.debugroom
 *
 */
@Controller
public class InfomationManagementController implements ServletContextAware{

	@Inject
	ServletContext servletContext;
	
	@Inject
	Mapper mapper;
	
	@Inject
	MessageSource messageSource;
	
	@ModelAttribute
	public NewInfomationForm setUpNewInfomationForm(){
		return NewInfomationForm.builder().build();
	}

	@ModelAttribute
	public InfomationDatailForm setUpInfomationDetailForm(){
		return InfomationDatailForm.builder().build();
	}

	@InitBinder
	public void initBinderForDate(WebDataBinder binder){
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    dateFormat.setLenient(false);
	    binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
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

	@RequestMapping(method = RequestMethod.GET, value="/not-infomation-viewers", params="infoId")
	public ResponseEntity<UserSearchResult> getNotInfomationViewers(@Validated UserSearchCriteria userSearchCriteria,
			Errors errors, Locale locale){
		
		String infoId = userSearchCriteria.getInfoId();
		UserSearchResult userSearchResult = UserSearchResult.builder()
												.infoId(infoId)
												.build();
		
		if(errors.hasErrors()){
			List<String> messages = new ArrayList<String>();
			userSearchResult.setMessages(messages);
			for(FieldError fieldError : errors.getFieldErrors()){
				messages.add(fieldError.getDefaultMessage());
			}
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(userSearchResult);
		}
		
		userSearchResult.setUsers(infomationManagementService.getNoViewers(infoId));
		return ResponseEntity.status(HttpStatus.OK).body(userSearchResult);

	}

	@RequestMapping(method = RequestMethod.POST, value = "/management/infomation/{infomation.infoId}")
	public String updateInfomation(@Validated InfomationUpdateForm infomationUpdateForm,
			BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes){
		
		if(bindingResult.hasErrors()){
			model.addAttribute(mapper.map(infomationUpdateForm, InfomationDetail.class));
			model.addAttribute(BindingResult.class.getName() + ".infomationDetail", bindingResult);
			return "management/infomation/detail";
		}

		try {
			redirectAttributes.addFlashAttribute(
					infomationManagementService.updateInfomation(
					mapper.map(infomationUpdateForm, InfomationDraft.class), 
					servletContext.getRealPath("")));
		} catch (BusinessException e) {
			model.addAttribute("errorCode", e.getCode());
			model.addAttribute(mapper.map(infomationUpdateForm, InfomationDetail.class));
			return "management/infomation/detail";
		}
		
		return new StringBuilder().append("redirect:")
					.append(infomationUpdateForm.getInfomation().getInfoId())
					.append("?complete")
					.toString();
	}
	
	@RequestMapping(method = RequestMethod.GET,
			value = "/management/infomation/{infomation.infoId}",
			params = "complete")
	public String updateComplete(){
		return "management/infomation/updateComplete";
	}
	
	@RequestMapping(method = RequestMethod.GET, value="/management/infomation/new")
	public String newInfomationForm(Model model){
		model.addAttribute("users", infomationManagementService.getUsers());
		return "management/infomation/form";
	}

	@RequestMapping(method = RequestMethod.POST, value="/management/infomation/draft/new")
	public String newInfomationDraft(@Validated(ConfirmInfomation.class) NewInfomationForm newInfomationForm,
			BindingResult bindingResult, Model model, Locale locale){
		
		if(bindingResult.hasErrors()){
			model.addAttribute("infomation", newInfomationForm);
			model.addAttribute(BindingResult.class.getName() + ".infomation", bindingResult);
			return newInfomationForm(model);
		}
		
		InfomationDraft infomationDraft = mapper.map(newInfomationForm, InfomationDraft.class);

		try {
			model.addAttribute(newInfomationForm.getUsers());
			model.addAttribute(infomationManagementService.
					createInfomationDraft(infomationDraft, servletContext.getRealPath("")));
		} catch (BusinessException e) {
			model.addAttribute("errorMessage", messageSource.getMessage(e.getCode(), e.getArgs(), locale));
			return "management/infomation/form";
		}
		return "management/infomation/confirm";
	}

	@RequestMapping(method = RequestMethod.POST, value="/management/infomation/new/{infoId}")
	public String saveInfomation(@Validated(SaveInfomation.class) NewInfomationForm newInfomationForm,
			 BindingResult bindingResult, Model model, 
			 RedirectAttributes redirectAttrivutes, Locale locale){
		
		InfomationDraft infomationDraft = mapper.map(newInfomationForm, InfomationDraft.class);

		if(bindingResult.hasErrors()){
			model.addAttribute(infomationDraft);
			model.addAttribute(BindingResult.class.getName() + ".infomationDraft", bindingResult);
			return "management/infomation/confirm";
		}

		if(!"save".equals(newInfomationForm.getType())){
			model.addAttribute("infomation", newInfomationForm);
			return newInfomationForm(model);
		}

		try {
			redirectAttrivutes.addFlashAttribute("users", infomationDraft.getViewUsers());
			redirectAttrivutes.addFlashAttribute(
					infomationManagementService.saveInfomation(infomationDraft));
		}catch (BusinessException e){
			model.addAttribute("errorCode", e.getCode());
			return "common/error";
		}

		return new StringBuilder().append("redirect:")
					.append(newInfomationForm.getInfoId())
					.append("?complete")
					.toString();
	}

	@RequestMapping(method = RequestMethod.GET,
			value = "/management/infomation/new/{infoId}",
			params = "complete")
	public String saveComplete(){
		return "management/infomation/saveComplete";
	}

	@Override
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}

}
