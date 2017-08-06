package org.debugroom.wedding.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	@Override
	public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(
                "/static/**");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
			.antMatchers("/favicon.ico").permitAll()
			.antMatchers("/portal/**").permitAll()
			.antMatchers("/information/**").permitAll()
			.antMatchers("/profile/**").permitAll()
			.antMatchers("/management/**").permitAll()
			.antMatchers("/search/**").permitAll()
			.antMatchers("/address/**").permitAll()
			.antMatchers("/image/**").permitAll()
			.antMatchers("/gallery/**").permitAll()
			.anyRequest().authenticated()
		.and()
			.csrf().disable()
			.formLogin()
			.loginProcessingUrl("/authenticate")
			.loginPage("/login")
			.failureUrl("/login")
			.usernameParameter("username")
			.passwordParameter("password")
			.permitAll()
		.and()
			.logout()
			.logoutSuccessUrl("/login")
			.permitAll();
	}
}
