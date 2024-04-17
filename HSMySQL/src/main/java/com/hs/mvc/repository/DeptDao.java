package com.hs.mvc.repository;

import com.hs.mvc.entity.Dept;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class DeptDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    //取得所有部門資料
    public List<Dept> queryAll() {
        String sql = "SELECT * FROM dept ORDER by id";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Dept.class));
    }
    
    
}
