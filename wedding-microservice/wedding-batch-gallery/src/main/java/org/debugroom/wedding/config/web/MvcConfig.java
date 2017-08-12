package org.debugroom.wedding.config.web;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@ComponentScan("org.debugroom.wedding.app.batch.gallery.listener.web")
@EnableWebMvc
public class MvcConfig extends WebMvcConfigurerAdapter{
}
