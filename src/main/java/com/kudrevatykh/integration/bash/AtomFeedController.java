package com.kudrevatykh.integration.bash;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ColumnMapRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AtomFeedController extends JdbcDaoSupport {
	
	@Autowired
	private AtomFeedView view;
	
	@Autowired
	public void setDS(DataSource ds) {
		setDataSource(ds);
	}
	
	private RowMapper<Map<String, Object>> mapper = new RowMapper<Map<String,Object>>() {
		
		@Override
		public Map<String, Object> mapRow(ResultSet rs, int rowNum)
				throws SQLException {
			Map<String, Object> m = new HashMap<String, Object>();
			m.put("id", rs.getString("id"));
			m.put("date", new Date(rs.getTimestamp("q_date").getTime()));
			try {
				m.put("text", IOUtils.toString(rs.getClob("text").getCharacterStream()));
			} catch (IOException e) {
				throw new SQLException(e);
			}
			return m;
		}
	};
	
	@RequestMapping("/")
	public ModelAndView getFeed() {
		List<Map<String, Object>> list = getJdbcTemplate().query("select * from quotes order by q_date desc, id desc fetch first 50 rows only", mapper);
		return new ModelAndView(view, "rss", list);
	}

}
