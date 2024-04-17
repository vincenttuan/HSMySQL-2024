package com.hs.mvc.repository.qa;

import com.hs.mvc.entity.qa.QAGroup;
import com.hs.mvc.entity.qa.QAItem;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class QADao {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    // 查詢 QAGroup
    public List<QAGroup> queryQAGroup() {
        String sql = "SELECT * FROM QAGroup";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(QAGroup.class));
    }
    
    // 查詢 QAItem
    public List<QAItem> queryQAItem() {
        String sql = "SELECT * FROM QAItem";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(QAItem.class));
    }
     
    
}
