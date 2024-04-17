//package com.hs.mvc.repository;
//
//import com.hs.mvc.entity.Employee;
//import java.util.List;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.jdbc.core.BeanPropertyRowMapper;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.stereotype.Repository;
//
//@Repository
//public class EmployeeDao {
//
//    
//    
//    @Autowired
//    private JdbcTemplate jdbcTemplate;
//    
//    // 變更Active
//    public int changeActive(String empNo, int active) {
//        String sql = "UPDATE Employee SET emp_active = ? WHERE emp_no = ?";
//        int rowCount = jdbcTemplate.update(sql, new Object[]{active, empNo});
//        return rowCount; // rowCount 表示異動筆數
//    }
//    
//    // 變更密碼
//    public int changePwd(String empNo, String oldPwd, String newPwd) {
//        String sql = "UPDATE Employee SET emp_pwd = ? WHERE emp_no = ? AND emp_pwd = ?";
//        int rowCount = jdbcTemplate.update(sql, new Object[]{newPwd, empNo, oldPwd});
//        return rowCount; // rowCount 表示異動筆數
//    }
//    
//    public String getNameByNo(String empNo) {
//        String sql = "SELECT emp_name FROM Employee Where emp_no = ? and emp_active=1";
//        try {
//            return (String) jdbcTemplate.queryForObject(sql, new Object[] { empNo }, String.class);
//        } catch (Exception e) {
//        }
//        return null;
//    }
//    
//    public Employee login(String username, String password) {
//        String sql = "SELECT * FROM Employee Where emp_no = ? and emp_pwd = ? and emp_active = 1";
//        try {
//            return jdbcTemplate.queryForObject(sql, new Object[] { username, password }, new BeanPropertyRowMapper<>(Employee.class));
//        } catch (Exception e) {
//        }
//        return null;
//    }
//    
//    // 查詢所有員工資料(在職)
//    public List<Employee> query() {
//        String sql = "SELECT * FROM Employee WHERE emp_active=1 Order by emp_no";
//        try {
//            return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Employee.class));
//        } catch (Exception e) {
//        }
//        return null;
//    }
//    
//    // 查詢所有員工資料
//    public List<Employee> queryAll() {
//        String sql = "SELECT * FROM Employee Order by emp_no";
//        try {
//            return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Employee.class));
//        } catch (Exception e) {
//        }
//        return null;
//    }
//    
//
//}
