package com.wizard.sdms.config;

import javax.sql.DataSource;

import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

@Configuration
public class MybatisConfig {
	@Autowired
	private DataSource dataSource;
	
	private SqlSessionFactoryBean sqlSessionFactoryBean() {
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		sqlSessionFactoryBean.setDataSource(dataSource);
		sqlSessionFactoryBean.setTypeAliasesPackage("com.wizard.sdms.baseinfo.model");
		
		sqlSessionFactoryBean.setMapperLocations(
				new Resource[] { 
						new ClassPathResource("com/wizard/sdms/*/mapper/xml/*.xml")	});  
		return sqlSessionFactoryBean;
	}
}
