package org.debugroom.wedding.app.web.security;

import java.util.Collection;

import javax.inject.Inject;

import org.debugroom.framework.common.exception.BusinessException;
import org.debugroom.wedding.domain.entity.Credential;
import org.debugroom.wedding.domain.entity.User;
import org.debugroom.wedding.domain.service.common.CommonDomainProperties;
import org.debugroom.wedding.domain.service.common.UserSharedService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CustomUserDetailService implements UserDetailsService{

	@Inject
	CommonDomainProperties properties;
	
	@Inject
	UserSharedService userSharedService;

	@Override
	public UserDetails loadUserByUsername(String username) 
			throws UsernameNotFoundException {
		
		try {
			User user = userSharedService.getUserByLoginId(username);
			for(Credential credential : user.getCredentials()){
				if(properties.getCredentialTypePasswordLogicalName().equals(
						credential.getId().getCredentialType())){
					return new CustomUserDetails(
						properties.getCredentialTypePasswordLogicalName(), 
						user, getAuthorities(user));
				}
			}
			throw new UsernameNotFoundException("authentication error", null);
		} catch (BusinessException e) {
			throw new UsernameNotFoundException("authentication error", e);
		}
	}

	private Collection<GrantedAuthority> getAuthorities(User user){
		if(user.getAuthorityLevel() == 9){
			return AuthorityUtils.createAuthorityList("ROLE_USER", "ROLE_ADMIN");
		}else{
			return AuthorityUtils.createAuthorityList("ROLE_USER");
		}
	}

}
