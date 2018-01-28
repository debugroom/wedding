package org.debugroom.wedding.config;

import javax.inject.Inject;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.debugroom.wedding.app.web.filter.MDCFilter;
import org.debugroom.wedding.app.web.helper.AuditLoggingHelper;
import org.debugroom.wedding.app.web.interceptor.ProfileAuditLoggingInterceptor;

@ComponentScan("org.debugroom.wedding.app.web")
@EnableWebMvc
public class MvcConfig extends WebMvcConfigurerAdapter{

	@Inject
	AuditLoggingHelper auditLoggingHelper;
	
	@Bean
	public ProfileAuditLoggingInterceptor auditLoggingInterceptor(){
		return new ProfileAuditLoggingInterceptor(auditLoggingHelper);
	}
	
	@Bean
	public FilterRegistrationBean mdcFilter(){
		FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
		filterRegistrationBean.setFilter(new MDCFilter());
		filterRegistrationBean.setOrder(0);
		return filterRegistrationBean;
	}

	@Bean
	public ViewResolver viewResolver(){
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setPrefix("/WEB-INF/views/");
		viewResolver.setSuffix(".jsp");
		return viewResolver;
	}
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry){
		registry.addResourceHandler("/static/**")
		        .addResourceLocations("classpath:/static/");
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry){
		registry.addInterceptor(auditLoggingInterceptor());
	}
	
}
