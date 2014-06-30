package com.kudrevatykh.integration.bash;

import org.springframework.integration.annotation.Filter;
import org.springframework.integration.annotation.Header;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

public class DbFilter extends JdbcDaoSupport {
	
	private String sqlQuery;
	
	@Filter
	public boolean filter(@Header("quoteId") String quoteId) {
		return getJdbcTemplate().queryForObject(sqlQuery, new Object[] {quoteId}, Integer.class)==0;
	}

	public String getSqlQuery() {
		return sqlQuery;
	}

	public void setSqlQuery(String sqlQuery) {
		this.sqlQuery = sqlQuery;
	}

}
