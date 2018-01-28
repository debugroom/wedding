package org.debugroom.wedding.app.web.management.request;

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
import org.debugroom.wedding.app.web.common.model.UserSearchCriteria;
import org.debugroom.wedding.app.web.common.model.UserSearchCriteria.GetNotRequestUsers;
import org.debugroom.wedding.app.web.common.model.UserSearchResult;
import org.debugroom.wedding.app.web.management.request.NewRequestForm.ConfirmRequest;
import org.debugroom.wedding.app.web.management.request.NewRequestForm.SaveRequest;
import org.debugroom.wedding.domain.management.model.RequestDetail;
import org.debugroom.wedding.domain.management.model.RequestDraft;
import org.debugroom.wedding.domain.management.service.RequestManagementService;

@Controller
public class RequestManagementController {

	@Inject
	Mapper mapper;
	
	@Inject
	RequestManagementService requestManagementService;
	
	@ModelAttribute
	public NewRequestForm setUpNewRequestForm(){
		return NewRequestForm.builder().build();
	}

	@InitBinder
	public void initBinderForDate(WebDataBinder binder){
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    dateFormat.setLenient(false);
	    binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}

	@RequestMapping(method = RequestMethod.GET, value="/management/request/portal")
	public String requestManagementPortal(@PageableDefault(
												page=0,
												size=10,
												direction = Direction.ASC,
												sort = {"requestId"}
												) Pageable pageable, Model model){
		model.addAttribute("page", requestManagementService
										.getRequestsUsingPage(pageable));
		return "management/request/portal";
	}

	@RequestMapping(method = RequestMethod.GET, value="/management/request/{requestId}")
	public String getRequest(@Validated RequestForm requestForm, 
			BindingResult bindingResult, @PageableDefault(
					page=0,
					size=10,
					direction = Direction.ASC,
					sort = {"requestId"}) Pageable pageable, Model model){
		if(bindingResult.hasErrors()){
			model.addAttribute("page", requestManagementService
											.getRequestsUsingPage(pageable));
			return "management/request/portal";
		}
		model.addAttribute(requestManagementService
				.getRequestDetail(requestForm.getRequestId()));
		if("detail".equals(requestForm.getType())){
			return "management/request/detail";
		}else{
			return "management/request/delete";
		}
	}

	@RequestMapping(method = RequestMethod.GET, value="/not-request-users", params="requestId")
	public ResponseEntity<UserSearchResult> getNotRequestUsers(
			@Validated(GetNotRequestUsers.class) UserSearchCriteria userSearchCriteria,
			Errors errors, Locale locale){
		
		String requestId = userSearchCriteria.getRequestId();
		UserSearchResult userSearchResult = UserSearchResult.builder()
												.requestId(requestId)
												.build();
		if(errors.hasErrors()){
			List<String> messages = new ArrayList<String>();
			userSearchResult.setMessages(messages);
			for(FieldError fieldError : errors.getFieldErrors()){
				messages.add(fieldError.getDefaultMessage());
			}
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(userSearchResult);
		}

		userSearchResult.setUsers(requestManagementService.getNotRequestUsers(requestId));
		return ResponseEntity.status(HttpStatus.OK).body(userSearchResult);
	}
	
	@RequestMapping(method = RequestMethod.POST, value="/management/request/{requestId}")
	public String updateRequest(@Validated RequestUpdateForm requestUpdateForm,
			BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes){
		
		if(bindingResult.hasErrors()){
			model.addAttribute(mapper.map(requestUpdateForm, RequestDetail.class));
			model.addAttribute(BindingResult.class.getName() + ".requestDetail", bindingResult);
			return "management/request/detail";
		}
		
		redirectAttributes.addFlashAttribute(
				requestManagementService.updateRequest(
						mapper.map(requestUpdateForm, RequestDraft.class)));

		return new StringBuilder().append("redirect:")
					.append(requestUpdateForm.getRequest().getRequestId())
					.append("?complete")
					.toString();
	}
	
	@RequestMapping(method = RequestMethod.GET, 
			value="/management/request/{request.requestId}",
			params = "complete")
	public String updateComplete(){
		return "management/request/updateComplete";
	}

	@RequestMapping(method = RequestMethod.GET, value="/management/request/new")
	public String newRequestForm(Model model){
		model.addAttribute("users", requestManagementService.getUsers());
		return "management/request/form";
	}

	@RequestMapping(method = RequestMethod.POST, value="/management/request/draft/new")
	public String newRequestDraft(@Validated(ConfirmRequest.class) NewRequestForm newRequestForm,
			BindingResult bindingResult, Model model, Locale locale){
		
		if(bindingResult.hasErrors()){
			model.addAttribute("request", newRequestForm);
			model.addAttribute(BindingResult.class.getName() + ".request", bindingResult);
			return newRequestForm(model);
		}
		
		RequestDraft requestDraft = mapper.map(newRequestForm, RequestDraft.class);
		
		model.addAttribute(newRequestForm.getCheckedUsers());
		model.addAttribute(requestManagementService.createRequestDraft(requestDraft));

		return "management/request/confirm";
		
	}
	
	@RequestMapping(method = RequestMethod.POST, value="/management/request/new/{requestId}")
	public String saveRequest(@Validated(SaveRequest.class) NewRequestForm newRequestForm,
			BindingResult bindingResult, Model model,
			RedirectAttributes redirectAttributes, Locale locale){
		
		RequestDraft requestDraft = mapper.map(newRequestForm, RequestDraft.class);
		
		if(bindingResult.hasErrors()){
			model.addAttribute(requestDraft);
			model.addAttribute(BindingResult.class.getName() + ".requestDraft", bindingResult);
			return "management/request/confirm";
		}
		
		if(!"save".equals(newRequestForm.getType())){
			model.addAttribute(newRequestForm);
			return newRequestForm(model);
		}
		
		redirectAttributes.addFlashAttribute("users", requestDraft.getAcceptors());
		redirectAttributes.addFlashAttribute(requestManagementService.saveRequest(requestDraft));

		return new StringBuilder().append("redirect:")
						.append(newRequestForm.getRequestId())
						.append("?complete")
						.toString();
	}
	
	@RequestMapping(method = RequestMethod.GET,
			value = "/management/request/new/{requestId}",
			params = "complete")
	public String saveComplete(){
		return "management/request/saveComplete";
	}
	
	@RequestMapping(method = RequestMethod.POST, value="/management/request/delete/{requestId}")
	public String deleteConfirm(@Validated DeleteRequestForm deleteRequestForm,
			BindingResult bindingResult, Model model,
			RedirectAttributes redirectAttributes){
		
		if(bindingResult.hasErrors()){
			return "common/error";
		}
		
		if(!"delete".equals(deleteRequestForm.getType())){
			return "redirect:/management/request/portal";
		}
		
		redirectAttributes.addFlashAttribute(
				requestManagementService.deleteRequest(
				deleteRequestForm.getRequestId()));
		
		return new StringBuilder().append("redirect:")
					.append(deleteRequestForm.getRequestId())
					.append("?complete")
					.toString();
	}
	
	@RequestMapping(method = RequestMethod.GET,
			value="/management/request/delete/{requestId}",
			params = "complete")
	public String deleteComplete(){
		return "management/request/deleteComplete";
	}

}
