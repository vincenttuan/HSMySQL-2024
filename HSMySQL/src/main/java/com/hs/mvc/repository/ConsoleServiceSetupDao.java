package com.hs.mvc.repository;

import com.hs.mvc.entity.ConsoleServiceSetup;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ConsoleServiceSetupDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // 新增使用功能與權限
    public int save(Integer cs_id, Integer css_emp_id, Integer css_priority) {
        String sql = "INSERT INTO consoleservicesetup (cs_id, css_emp_id, css_priority) "
                   + "VALUES(?, ?, ?)";
        Object[] args = {cs_id, css_emp_id, css_priority};
        int rowCount = jdbcTemplate.update(sql, args);
        return rowCount;
    }
    
    // 刪除功能權限 By EmpId
    public int deleteByEmpId(Integer empId) {
        String sql = "DELETE FROM consoleservicesetup WHERE css_emp_id = ?";
        Object[] args = { empId };
        int rowCount = jdbcTemplate.update(sql, args);
        return rowCount;
    }
    
    // 根據 empId 查詢功能與權限
    public List<ConsoleServiceSetup> getByEmpId(Integer empId){
        String sql = "SELECT * FROM consoleservicesetup WHERE css_emp_id = ?";
        return jdbcTemplate.query(sql, new Object[]{ empId }, new BeanPropertyRowMapper<>(ConsoleServiceSetup.class));
    }
}
