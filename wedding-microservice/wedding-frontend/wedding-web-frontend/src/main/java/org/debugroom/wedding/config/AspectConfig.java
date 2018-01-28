package org.debugroom.wedding.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableAspectJAutoProxy
@ComponentScan("org.debugroom.wedding.app.web.interceptor")
public class AspectConfig {
}
