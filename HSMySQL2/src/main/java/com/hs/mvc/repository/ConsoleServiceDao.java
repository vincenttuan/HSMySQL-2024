package com.hs.mvc.repository;

import com.hs.mvc.entity.ClockOn;
import com.hs.mvc.entity.ConsoleService;
import com.hs.mvc.entity.ConsoleServiceSetup;
import java.util.Comparator;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ConsoleServiceDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<ConsoleService> queryAll() {
        String sql = "SELECT * FROM ConsoleService order by cs_id";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(ConsoleService.class));
    }
    
    
    public List<ConsoleServiceSetup> queryByEmpId(Integer empId) {
        String sql = "SELECT * "
                + "FROM ConsoleServiceSetup css "
                + "WHERE css.css_emp_id = ?";
        List<ConsoleServiceSetup> list = jdbcTemplate.query(sql, new Object[]{empId}, new BeanPropertyRowMapper<>(ConsoleServiceSetup.class));
        
        for(ConsoleServiceSetup css : list) { // 組合 ConsoleService
            sql = "SELECT * FROM ConsoleService cs WHERE cs.cs_id = ?";
            ConsoleService cs = jdbcTemplate.queryForObject(sql, new Object[]{css.getCsId()}, new BeanPropertyRowMapper<>(ConsoleService.class));
            css.setCs(cs);
        }
        
        // 排序邏輯
        list.sort((o1, o2) -> o1.getCs().getSortId() - o2.getCs().getSortId());
        return list;
    }
}
