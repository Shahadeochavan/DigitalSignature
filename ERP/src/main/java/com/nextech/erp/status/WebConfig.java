package com.nextech.erp.status;

import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

public class WebConfig extends WebMvcConfigurerAdapter  {

	@Override
	public void addCorsMappings(CorsRegistry registry) {
	registry.addMapping("/api/**")
	.allowedOrigins("http://localhost:8080")
	.allowedMethods("PUT", "DELETE")
	.allowedHeaders("header1", "header2", "header3")
	.exposedHeaders("header1", "header2")
	.allowCredentials(false).maxAge(3600);
	}
}
