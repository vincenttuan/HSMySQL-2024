package com.hs.mvc.repository;

import com.hs.mvc.entity.Status;
import java.util.List;
import javax.servlet.ServletContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

@Repository
public class StatusDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    public List<Status> queryAllList() {
        String versionSql = "SELECT version FROM hsversion WHERE expireddate >= date(now()) AND table_name = 'status' order by version limit 1";
        int version = jdbcTemplate.queryForObject(versionSql, Integer.class);
        String sql = "SELECT * FROM Status WHERE remove = false AND version = " + version;
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Status.class));
    }
    
}
