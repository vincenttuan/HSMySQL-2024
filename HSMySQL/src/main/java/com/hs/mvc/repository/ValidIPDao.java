package com.hs.mvc.repository;

import com.hs.mvc.entity.ValidIP;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ValidIPDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    //查詢所有核准IP
    public List<ValidIP> queryAllValidIPs() {
        String sql = "SELECT id, ip, device, content, status, ct FROM validip WHERE status = 1";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(ValidIP.class));
    }
}
