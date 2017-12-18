package com.wizard.sdms.application;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource(value = { "classpath:jdbc.properties" })
public class SdmsApplication {
	public static void main(String[] args) {
		
	}
}
