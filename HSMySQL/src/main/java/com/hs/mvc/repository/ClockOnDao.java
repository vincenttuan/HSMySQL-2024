package com.hs.mvc.repository;

import com.hs.mvc.entity.ClockOn;
import com.hs.mvc.entity.ClockOnException;
import com.hs.mvc.entity.Status;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ClockOnDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    public List<ClockOn> queryByEmpNoToday(String empNo) {
        String sql = "SELECT c.clock_id, c.emp_no, c.status_id, c.clock_on, c.image, e.emp_name, s.status_name\n" +
                     "FROM ClockOn c, Employee e, Status s\n" +
                     "Where c.emp_no = ? AND \n" +
                     "      DATE(c.clock_on) = DATE(now()) AND\n" +
                     "      c.emp_no = e.emp_no AND\n" +
                     "      c.status_id = s.status_id";
        System.out.println(sql);
        return jdbcTemplate.query(sql, new Object[]{empNo}, new BeanPropertyRowMapper<>(ClockOn.class));
    }
    
    public List<ClockOn> queryAllBetween(String empNo, String d1, String d2) {
        String sql = "";
        if(empNo.trim().equals("")) {
            sql = "SELECT c.clock_id, c.emp_no, c.status_id, c.clock_on, c.image, e.emp_name, s.status_name\n" +
                  "FROM ClockOn c, Employee e, Status s \n" +
                  "WHERE c.clock_on BETWEEN ? AND \n" +
                  "      DATE_ADD(?, INTERVAL 1 DAY) AND\n" +
                  "      c.emp_no = e.emp_no AND\n" +
                  "      c.status_id = s.status_id";
            return jdbcTemplate.query(sql, new Object[]{d1, d2}, new BeanPropertyRowMapper<>(ClockOn.class));
        } else {
            sql = "SELECT c.clock_id, c.emp_no, c.status_id, c.clock_on, c.image, e.emp_name, s.status_name\n" +
                  "FROM ClockOn c, Employee e, Status s \n" +
                  "WHERE c.emp_no = ? AND \n" +
                  "      c.clock_on BETWEEN ? AND DATE_ADD(?, INTERVAL 1 DAY) AND\n" +
                  "      c.emp_no = e.emp_no AND\n" +
                  "      c.status_id = s.status_id";
            return jdbcTemplate.query(sql, new Object[]{empNo, d1, d2}, new BeanPropertyRowMapper<>(ClockOn.class));
        }
    }
    
    public void save(ClockOn clockOn) {
        String sql = "Insert Into CLOCKON(emp_no, status_id, clock_on, image) values(?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                clockOn.getEmpNo(), clockOn.getStatusId(), new Date(), clockOn.getImage());
        
    }
    
    
}
