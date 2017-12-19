package com.wizard.sdms.config;

import org.springframework.context.annotation.Bean;
import org.springframework.web.filter.CharacterEncodingFilter;

public class FilterConfig {
	@Bean
	public CharacterEncodingFilter characterEncodingFilter() {
		  CharacterEncodingFilter characterEncodingFilter =new CharacterEncodingFilter();
		  characterEncodingFilter.setEncoding("UTF-8");
		  characterEncodingFilter.setForceEncoding(true);
		  return characterEncodingFilter;
		}
}
