package org.debugroom.wedding.app.web.management;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import org.dozer.Mapper;
import org.dozer.MappingException;
import org.debugroom.framework.common.exception.BusinessException;
import org.debugroom.wedding.app.model.management.information.InformationFormResource;
import org.debugroom.wedding.app.model.management.request.RequestFormResource;
import org.debugroom.wedding.domain.entity.management.Request;
import org.debugroom.wedding.domain.entity.Address;
import org.debugroom.wedding.domain.entity.Credential;
import org.debugroom.wedding.domain.entity.CredentialPK;
import org.debugroom.wedding.domain.entity.Email;
import org.debugroom.wedding.domain.entity.EmailPK;
import org.debugroom.wedding.domain.entity.Information;
import org.debugroom.wedding.domain.entity.User;
import org.debugroom.wedding.domain.model.common.UpdateResult;
import org.debugroom.wedding.domain.model.management.InformationDetail;
import org.debugroom.wedding.domain.model.management.InformationDraft;
import org.debugroom.wedding.domain.model.management.RequestDetail;
import org.debugroom.wedding.domain.model.management.RequestDraft;
import org.debugroom.wedding.domain.service.management.InformationManagementService;
import org.debugroom.wedding.domain.service.management.RequestManagementService;
import org.debugroom.wedding.domain.service.management.UserManagementService;

@RestController
@RequestMapping("/api/v1")
public class ManagementRestController {

	@Inject
	Mapper mapper;
	
	@Inject
	UserManagementService userManagementService;
	
	@Inject
	InformationManagementService informationManagementService;
	
	@Inject
	RequestManagementService requestManagementService;
	
	@RequestMapping(method=RequestMethod.GET, value="/users")
	public Page<User> getUsers(@PageableDefault(
			page=0, size=10, direction=Direction.ASC, sort={"userId"}) Pageable pageable){
		return userManagementService.getUsersUsingPage(pageable);
	}


	@RequestMapping(method=RequestMethod.GET, value="/users/{loginId:.+}")
	public User getUserByLoginId(@PathVariable String loginId) {
		try {
			return userManagementService.getUser(loginId);
		} catch (BusinessException e) {
			return User.builder().build(); 
		}
	}


	@RequestMapping(method=RequestMethod.POST, value="/user/profile/new")
	public User createUserProfile(@RequestBody 
			org.debugroom.wedding.app.model.management.user.User user) 
			throws MappingException, BusinessException{
		Set<Email> emails = new HashSet<Email>();
		for(org.debugroom.wedding.app.model.management.user.Email email : user.getEmails()){
			emails.add(Email.builder()
					.id(EmailPK.builder().emailId(email.getId().getEmailId())
							.userId(email.getId().getUserId()).build())
					.email(email.getEmail())
					.build());
		}
		Set<Credential> credentials = new HashSet<Credential>();
		for(org.debugroom.wedding.app.model.management.user.Credential credential : user.getCredentials()){
			credentials.add(Credential.builder()
					.id(CredentialPK.builder()
							.userId(credential.getId().getUserId())
							.credentialType(credential.getId().getCredentialType())
							.build())
					.credentialKey(credential.getCredentialKey())
					.build());
		}
		return userManagementService.createUserProfile(
				User.builder()
				.userId(user.getUserId())
				.firstName(user.getFirstName())
				.lastName(user.getLastName())
				.loginId(user.getLoginId())
				.authorityLevel(user.getAuthorityLevel())
				.imageFilePath(user.getImageFilePath())
				.isBrideSide(user.isBrideSide())
				.address(Address.builder()
						.postCd(user.getAddress().getPostCd())
						.address(user.getAddress().getAddress())
						.build())
				.emails(emails)
				.credentials(credentials)
				.build());
	}

	@RequestMapping(method=RequestMethod.POST, value="/user/{userId}")
	public User saveUser(@RequestBody
			org.debugroom.wedding.app.model.management.user.User user) 
					throws MappingException, BusinessException{
		Set<Email> emails = new HashSet<Email>();
		for(org.debugroom.wedding.app.model.management.user.Email email : user.getEmails()){
			emails.add(Email.builder()
					.id(EmailPK.builder().emailId(email.getId().getEmailId())
							.userId(email.getId().getUserId()).build())
					.email(email.getEmail())
					.build());
		}
		Set<Credential> credentials = new HashSet<Credential>();
		for(org.debugroom.wedding.app.model.management.user.Credential credential : user.getCredentials()){
			credentials.add(Credential.builder()
					.id(CredentialPK.builder()
							.userId(credential.getId().getUserId())
							.credentialType(credential.getId().getCredentialType())
							.build())
					.credentialKey(credential.getCredentialKey())
					.build());
		}
		return userManagementService.saveUser(
				User.builder()
				.userId(user.getUserId())
				.firstName(user.getFirstName())
				.lastName(user.getLastName())
				.loginId(user.getLoginId())
				.authorityLevel(user.getAuthorityLevel())
				.imageFilePath(user.getImageFilePath())
				.isBrideSide(user.isBrideSide())
				.address(Address.builder()
						.postCd(user.getAddress().getPostCd())
						.address(user.getAddress().getAddress())
						.build())
				.emails(emails)
				.credentials(credentials)
				.build());
	}

	@RequestMapping(method=RequestMethod.GET, value="/user/{userId}")
	public User getUser(@PathVariable String userId) throws BusinessException{
		return userManagementService.getUserProfile(userId);
	}

	@RequestMapping(method=RequestMethod.PUT, value="/user/{userId}")
	public UpdateResult<User> updateUser(@RequestBody
			org.debugroom.wedding.app.model.management.user.User user) 
					throws MappingException, BusinessException{
		Set<Email> emails = new HashSet<Email>();
		for(org.debugroom.wedding.app.model.management.user.Email email : user.getEmails()){
			emails.add(Email.builder()
					.id(EmailPK.builder().emailId(email.getId().getEmailId())
							.userId(email.getId().getUserId()).build())
					.email(email.getEmail())
					.build());
		}
		Set<Credential> credentials = new HashSet<Credential>();
		for(org.debugroom.wedding.app.model.management.user.Credential credential : user.getCredentials()){
			credentials.add(Credential.builder()
					.id(CredentialPK.builder()
							.userId(credential.getId().getUserId())
							.credentialType(credential.getId().getCredentialType())
							.build())
					.credentialKey(credential.getCredentialKey())
					.build());
		}
		return userManagementService.udpateUser(
				User.builder()
				.userId(user.getUserId())
				.firstName(user.getFirstName())
				.lastName(user.getLastName())
				.loginId(user.getLoginId())
				.authorityLevel(user.getAuthorityLevel())
				.imageFilePath(user.getImageFilePath())
				.isBrideSide(user.isBrideSide())
				.address(Address.builder()
						.postCd(user.getAddress().getPostCd())
						.address(user.getAddress().getAddress())
						.build())
				.emails(emails)
				.credentials(credentials)
				.build());
	}

	@RequestMapping(method=RequestMethod.DELETE, value="/user/{userId}")
	public User deleteUser(@RequestBody
			org.debugroom.wedding.app.model.management.user.User user){
		return userManagementService.deleteUser(user.getUserId());
	}

	@RequestMapping(method=RequestMethod.GET, value="/profile/{userId}")
	public User getProfile(@PathVariable String userId) throws BusinessException{
		return userManagementService.getUserProfile(userId);
	}

	@RequestMapping(method=RequestMethod.GET, value="/information")
	public Page<Information> getInformation(@PageableDefault(
			page=0, size=10, direction=Direction.ASC, sort={"infoId"}) Pageable pageable){
		return informationManagementService.getInformationUsingPage(pageable);
	}

	@RequestMapping(method=RequestMethod.GET, value="/information/form")
	public InformationFormResource getInformationForm(){
		return InformationFormResource.builder().users(
				informationManagementService.getUsers()).build();
	}

	@RequestMapping(method=RequestMethod.POST, value="/information/draft/new")
	public InformationDraft createInformationDraft(@RequestBody 
			org.debugroom.wedding.app.model.management.information.Information information) 
					throws MappingException, BusinessException{
//		return informationManagementService.createInformationDraft(
//				mapper.map(information, InformationDraft.class));
		List<User> users = new ArrayList<User>();
		for(org.debugroom.wedding.app.model.management.information.User user : information.getCheckedUsers()){
			users.add(User.builder()
					.userId(user.getUserId())
					.firstName(user.getFirstName())
					.lastName(user.getLastName())
					.build());
		}
		return informationManagementService.createInformationDraft(
				InformationDraft.builder()
				.information(Information.builder()
						.infoId(information.getInfoId())
						.infoPagePath(information.getInfoPagePath())
						.title(information.getTitle())
						.registratedDate(information.getRegistratedDate())
						.releaseDate(information.getReleaseDate())
						.build())
				.infoName(information.getInfoName())
				.viewUsers(users)
				.build());
	}
	
	@RequestMapping(method=RequestMethod.POST, value="/information/{infoId}")
	public Information saveInformation(@RequestBody
			org.debugroom.wedding.app.model.management.information.Information information)
					throws MappingException, BusinessException{
//		return informationManagementService.saveInformation(
//				mapper.map(information, InformationDraft.class));
		List<User> users = new ArrayList<User>();
		for(org.debugroom.wedding.app.model.management.information.User user : information.getCheckedUsers()){
			users.add(User.builder()
					.userId(user.getUserId())
					.build());
		}
		return informationManagementService.saveInformation(
				InformationDraft.builder()
				.information(Information.builder()
						.infoId(information.getInfoId())
						.infoPagePath(information.getInfoPagePath())
						.title(information.getTitle())
						.registratedDate(information.getRegistratedDate())
						.releaseDate(information.getReleaseDate())
						.build())
				.infoName(information.getInfoName())
				.viewUsers(users)
				.build());
	}

	@RequestMapping(method=RequestMethod.GET, value="/information/{infoId}")
	public InformationDetail getInformation(@PathVariable String infoId){
		return informationManagementService.getInformationDetail(infoId);
	}

	@RequestMapping(method=RequestMethod.GET, value="/users", 
			params="not-information-viewers")
	public org.debugroom.wedding.app.model.management.information.UserSearchResult 
		getNotInformationViewers(@RequestParam("infoId") String infoId){
		return org.debugroom.wedding.app.model.management.information.UserSearchResult
				.builder()
				.infoId(infoId)
				.users(informationManagementService.getNoViewers(infoId))
				.build();
	}

	@RequestMapping(method=RequestMethod.GET, value="/users",
			params="not-request-users")
	public org.debugroom.wedding.app.model.management.request.UserSearchResult 
		getNotRequestUsers(@RequestParam("requestId") String requestId){
		return org.debugroom.wedding.app.model.management.request.UserSearchResult
				.builder()
				.requestId(requestId)
				.users(requestManagementService.getNotRequestUsers(requestId))
				.build();
	}

	@RequestMapping(method=RequestMethod.PUT, value="/information/{infoId}")
	public UpdateResult<InformationDetail> updateInformation(@RequestBody
			org.debugroom.wedding.app.model.management.information.InformationDraft informationDraft) 
					throws MappingException, BusinessException{
	//	return informationManagementService.updateInformation(
	//			mapper.map(informationDraft, InformationDraft.class));
		List<User> addUsers = new ArrayList<User>();
		List<User> deleteUsers = new ArrayList<User>();
		if(null != informationDraft.getCheckedAddUsers()){
			for(org.debugroom.wedding.app.model.management.information.User user : informationDraft.getCheckedAddUsers()){
				addUsers.add(User.builder().userId(user.getUserId()).build());
			}
		}
		if(null != informationDraft.getCheckedDeleteUsers()){
			for(org.debugroom.wedding.app.model.management.information.User user : informationDraft.getCheckedDeleteUsers()){
				deleteUsers.add(User.builder().userId(user.getUserId()).build());
			}
		}
		return informationManagementService.updateInformation(
				InformationDraft.builder().information(
						Information.builder()
						.infoId(informationDraft.getInformation().getInfoId())
						.infoPagePath(informationDraft.getInformation().getInfoPagePath())
						.title(informationDraft.getInformation().getTitle())
						.registratedDate(informationDraft.getInformation().getRegistratedDate())
						.releaseDate(informationDraft.getInformation().getReleaseDate())
						.build())
				.excludeUsers(deleteUsers)
				.viewUsers(addUsers)
				.build());
	}

	@RequestMapping(method=RequestMethod.DELETE, value="/information/{infoId}")
	public Information deleteInformation(@RequestBody 
			org.debugroom.wedding.app.model.management.information.Information information){
		return informationManagementService.deleteInformation(information.getInfoId());
	}

	@RequestMapping(method=RequestMethod.GET, value="/requests")
	public Page<Request> getRequests(@PageableDefault(
			page=0, size=10, direction=Direction.ASC, sort={"requestId"}) Pageable pageable){
		return requestManagementService.getReqesutsUsingPage(pageable);
	}

	@RequestMapping(method=RequestMethod.GET, value="/request/form")
	public RequestFormResource getRequestForm(){
		return RequestFormResource.builder().users(
				requestManagementService.getUsers()).build();
	}

	@RequestMapping(method=RequestMethod.POST, value="/request/draft/new")
	public RequestDraft createRequestDraft(@RequestBody
			org.debugroom.wedding.app.model.management.request.Request request){
//		return requestManagementService.createRequestDraft(
//				mapper.map(request, RequestDraft.class));
		List<org.debugroom.wedding.domain.entity.management.User> addUsers 
			= new ArrayList<org.debugroom.wedding.domain.entity.management.User>();
		if(null != request.getCheckedUsers() 
				&& request.getCheckedUsers().size() != 0){
		for(org.debugroom.wedding.app.model.management.request.User user 
					: request.getCheckedUsers()){
				addUsers.add(org.debugroom.wedding.domain.entity.management.User
						.builder()
						.userId(user.getUserId())
						.firstName(user.getFirstName())
						.lastName(user.getLastName())
						.build());
			}
		}
		return requestManagementService.createRequestDraft(
				RequestDraft.builder()
				.request(Request.builder()
						.requestId(request.getRequestId())
						.title(request.getTitle())
						.requestContents(request.getRequestContents())
						.registratedDate(request.getRegistratedDate())
						.build())
				.acceptors(addUsers)
				.build());
	}

	@RequestMapping(method=RequestMethod.POST, value="/request/{requestId}")
	public Request saveRequest(@RequestBody
			org.debugroom.wedding.app.model.management.request.Request request){
//		return requestManagementService.saveRequest(
//				mapper.map(request, RequestDraft.class));
		List<org.debugroom.wedding.domain.entity.management.User> addUsers 
			= new ArrayList<org.debugroom.wedding.domain.entity.management.User>();
		if(null != request.getCheckedUsers() 
				&& request.getCheckedUsers().size() != 0){
			for(org.debugroom.wedding.app.model.management.request.User user 
					: request.getCheckedUsers()){
				addUsers.add(org.debugroom.wedding.domain.entity.management.User
						.builder().userId(user.getUserId()).build());
			}
		}
		return requestManagementService.saveRequest(
				RequestDraft.builder()
				.request(Request.builder()
						.requestId(request.getRequestId())
						.title(request.getTitle())
						.requestContents(request.getRequestContents())
						.registratedDate(request.getRegistratedDate())
						.build())
				.acceptors(addUsers)
				.build());
	}
	
	@RequestMapping(method=RequestMethod.GET, value="/request/body/{requestId}",
			produces = "text/html; charset=UTF-8")
	public String getReqeustBody(@PathVariable String requestId){
		return requestManagementService.getRequest(requestId).getRequestContents();
	}

	@RequestMapping(method=RequestMethod.GET, value="/request/{requestId}")
	public RequestDetail getRequestDetail(@PathVariable String requestId){
		return requestManagementService.getRequestDetail(requestId);
	}

	@RequestMapping(method=RequestMethod.PUT, value="/request/{requestId}")
	public UpdateResult<RequestDetail> updateRequest(@RequestBody
			org.debugroom.wedding.app.model.management.request.RequestDraft requestDraft){
//		return requestManagementService.updateRequest(
//				mapper.map(requestDraft, RequestDraft.class));
		List<org.debugroom.wedding.domain.entity.management.User> addUsers 
			= new ArrayList<org.debugroom.wedding.domain.entity.management.User>();
		List<org.debugroom.wedding.domain.entity.management.User> deleteUsers 
			= new ArrayList<org.debugroom.wedding.domain.entity.management.User>();
		if(null != requestDraft.getCheckedAddUsers() 
				&& requestDraft.getCheckedAddUsers().size() != 0){
			for(org.debugroom.wedding.app.model.management.request.User user 
					: requestDraft.getCheckedAddUsers()){
				addUsers.add(org.debugroom.wedding.domain.entity.management.User
						.builder().userId(user.getUserId()).build());
			}
		}
		if(null != requestDraft.getCheckedDeleteUsers() 
				&& requestDraft.getCheckedDeleteUsers().size() != 0){
			for(org.debugroom.wedding.app.model.management.request.User user 
					: requestDraft.getCheckedDeleteUsers()){
				deleteUsers.add(org.debugroom.wedding.domain.entity.management.User
						.builder().userId(user.getUserId()).build());
			}
		}
		return requestManagementService.updateRequest(
				RequestDraft.builder()
				.request(Request.builder()
						.requestId(requestDraft.getRequest().getRequestId())
						.title(requestDraft.getRequest().getTitle())
						.requestContents(requestDraft.getRequest().getRequestContents())
						.registratedDate(requestDraft.getRequest().getRegistratedDate())
						.build())
				.acceptors(addUsers)
				.excludeUsers(deleteUsers)
				.build());
	}

	@RequestMapping(method=RequestMethod.DELETE, value="/request/{requestId}")
	public Request deleteRequest(@PathVariable String requestId){
		return requestManagementService.deleteRequest(requestId);
	}

}
