package com.wizard.sdms.config.spring;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

import com.alibaba.druid.pool.DruidDataSource;




public class SpringConfig {
	@Value("${jdbc.driver}")
	private String driverClass;
	@Value("${jdbc.url}")
	private String jdbcUrl;
	@Value("${jdbc.username}")
	private String username;
	@Value("${jdbc.password}")
	private String password;
	@Bean(destroyMethod = "close")
	private DataSource dataSource() {
		DruidDataSource druidDataSource  = new DruidDataSource();
		druidDataSource.setDriverClassName(driverClass);
		druidDataSource.setUrl(jdbcUrl);
		druidDataSource.setUsername(username);
		druidDataSource.setPassword(password);
		/**
		 * 检查数据库连接池中空闲连接的间隔时间，单位是分就，默认值：240，如果要取消则设置为0
		 */
		druidDataSource.setTimeBetweenEvictionRunsMillis(60000);
		
		/**
		 * 连接池中未使用的链接最大存活时间，单位是分，默认值：60，如果要永远存活设置为0
		 */
//		boneCPdataSource.setIdleMaxAgeInMinutes(30);
		/**
		 * 每个分区最大的连接数
		 */
		druidDataSource.setMaxActive(100);
		/**
		 * 每个分区最小的连接数
		 */
		druidDataSource.setMinIdle(5);
		return druidDataSource;
	}
}
