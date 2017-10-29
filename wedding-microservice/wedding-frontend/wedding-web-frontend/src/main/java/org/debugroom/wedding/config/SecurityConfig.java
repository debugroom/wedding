package org.debugroom.wedding.config;

import javax.inject.Inject;

import org.debugroom.wedding.app.web.security.LoginFailureHandler;
import org.debugroom.wedding.app.web.security.LoginSuccessHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	@Inject
	PasswordEncoder passwordEncoder;
	
	@Inject
	UserDetailsService userDetailsService;
	
	@Override
	public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(
                "/static/**");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
			.antMatchers("/favicon.ico").permitAll()
			.antMatchers("/webjars/**").permitAll()
/*
			.antMatchers("/portal/**").permitAll()
			.antMatchers("/information/**").permitAll()
			.antMatchers("/profile/**").permitAll()
			.antMatchers("/management/**").permitAll()
			.antMatchers("/search/**").permitAll()
			.antMatchers("/address/**").permitAll()
			.antMatchers("/image/**").permitAll()
			.antMatchers("/gallery/**").permitAll()
			.antMatchers("/chat/**").permitAll()
			.antMatchers("/messages/**").permitAll()
*/
			.anyRequest().authenticated()
		.and()
			.csrf().disable()
			.formLogin()
			.loginProcessingUrl("/authenticate")
			.loginPage("/login")
			.successForwardUrl("/portal")
			.failureUrl("/login")
			.successHandler(new LoginSuccessHandler())
//			.failureHandler(new LoginFailureHandler())
			.usernameParameter("username")
			.passwordParameter("password")
			.permitAll()
		.and()
			.logout()
			.logoutSuccessUrl("/login")
			.permitAll();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth
		.userDetailsService(userDetailsService)
		.passwordEncoder(passwordEncoder);
	}
	
}
