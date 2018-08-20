package com.yonyou.cloud.ops.task.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.dexcoder.dal.JdbcDao;
import com.dexcoder.dal.SimpleSqlFactory;
import com.dexcoder.dal.handler.DefaultMappingHandler;
import com.dexcoder.dal.spring.JdbcDaoImpl;

/**
 * 
 * @author daniell
 *
 */
@Configuration
@EnableTransactionManagement
public class DatasourceConfig {
	@Bean
	public JdbcDao jdbcDao(JdbcOperations jdbcOperations) throws Exception {
		JdbcDaoImpl jdbcDao = new JdbcDaoImpl();
		jdbcDao.setJdbcTemplate(jdbcOperations);
		jdbcDao.setSqlFactory(new SimpleSqlFactory());
		jdbcDao.setMappingHandler(new DefaultMappingHandler());
		return jdbcDao;
	}

}
