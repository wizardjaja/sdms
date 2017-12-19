package com.wizard.sdms.config;

import javax.sql.DataSource;
import org.apache.ibatis.logging.log4j.Log4jImpl;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.context.annotation.Configuration;
/**
 * mybatis配置文件
 * @author wizard
 *
 */
@Configuration
public class MybatisConfig {
	@Autowired
	private DataSource dataSource;
	/**
	 * 获取sqlSessionFactoryBean 对象
	 * @return
	 */
	@Bean
	@ConditionalOnMissingBean   //当容器里没有指定的Bean的情况下创建该对象
	public SqlSessionFactoryBean sqlSessionFactoryBean() {
		//生成SqlSessionFactoryBean对象
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		sqlSessionFactoryBean.setDataSource(dataSource);
		//设置别名所在的路径
		sqlSessionFactoryBean.setTypeAliasesPackage("com.wizard.sdms.*.model");
		//设置mapper类所在的路径
		sqlSessionFactoryBean.setMapperLocations(
				new Resource[] { 
						new ClassPathResource("com/wizard/sdms/*/mapper/xml/*.xml")	});
		//生成一个mybatis配置对象
		org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
		//设置开启缓存
		configuration.setCacheEnabled(true);
		Log4jImpl log4jImpl = new Log4jImpl("LOG4J");
		//设置日志格式
		configuration.setLogImpl(log4jImpl.getClass());
		sqlSessionFactoryBean.setConfiguration(configuration);
		return sqlSessionFactoryBean;
	}
	/**
	 * 获取sqlSessionTemplate 对象
	 * @return
	 */
	@Bean
	public SqlSessionTemplate sqlSessionTemplate() {
		TransactionFactory transactionFactory = new JdbcTransactionFactory();
		Environment environment = new Environment("development", transactionFactory, dataSource);
		org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration(environment);
		configuration.addMappers("com.wizard.sdms.*.mapper");
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(configuration);
		SqlSessionTemplate sqlSessionTemplate = new SqlSessionTemplate(sqlSessionFactory);
		return sqlSessionTemplate;
	}
	/**
	 * 获得Mapper接口对象
	 * @return
	 */
	@Bean
	public MapperScannerConfigurer mapperScannerConfigurer() {
		MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
		mapperScannerConfigurer.setBasePackage("com.wizard.sdms.*.mapper");
		return mapperScannerConfigurer;
	}
}
