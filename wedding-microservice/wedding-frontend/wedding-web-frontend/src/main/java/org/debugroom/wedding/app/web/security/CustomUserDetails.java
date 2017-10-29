package org.debugroom.wedding.app.web.security;

import java.util.Collection;

import org.debugroom.wedding.domain.entity.Credential;
import org.debugroom.wedding.domain.entity.User;
import org.debugroom.wedding.domain.service.common.DateUtil;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Data;

@Data
public class CustomUserDetails implements UserDetails{

	private static final long serialVersionUID = -3078013902554804433L;

	private final String credentialTypePasswordLogicalName;
	private final User user;
	private final Collection<GrantedAuthority> authorities;
	
	public CustomUserDetails(String credentialTypePasswordLogicalName, 
			User user, Collection<GrantedAuthority> authorities){
		this.credentialTypePasswordLogicalName = credentialTypePasswordLogicalName;
		this.user = user;
		this.authorities = authorities;
	}
	
	@Override
	public Collection<GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		for(Credential credential : user.getCredentials()){
			if(credentialTypePasswordLogicalName.equals(
					credential.getId().getCredentialType())){
				return credential.getCredentialKey();
			}
		}
		return null;
	}

	@Override
	public String getUsername() {
		return user.getLoginId();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		for(Credential credential : user.getCredentials()){
			if(credentialTypePasswordLogicalName.equals(
					credential.getId().getCredentialType())){
				if(DateUtil.getCurrentDate().getTime() 
						<= credential.getValidDate().getTime()){
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
