package com.hs.mvc.repository;

import com.hs.mvc.entity.Employee;
import java.util.Base64;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class EmployeeDao {

    final Base64.Decoder decoder = Base64.getDecoder();
    final Base64.Encoder encoder = Base64.getEncoder();

    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    // 取得最員工編號
    public int getNextEmpNo(int depyId) {
        String sql = "SELECT CONVERT(REPLACE(emp_no, 'HS', ''), UNSIGNED INTEGER) + 10 as next_emp_no \n"
                + "FROM employee \n"
                + "where dept_id1 = ? and LENGTH(emp_no) = 7 and emp_no != 'HS99001'\n"
                + "order by emp_no desc limit 1";
        Object[] args = {depyId};
        return jdbcTemplate.queryForObject(sql, args, Integer.class);
    }


    // 新增員工
    public int save(String emp_no, String emp_name, String emp_active, String emp_rfid, String agent1_id,
            String emp_priority, String dept_id1, String dept_id2) {
        String emp_pwd = "";
        try {
            //密碼預設是員編
            emp_pwd = encoder.encodeToString(emp_no.getBytes("UTF-8"));
        } catch (Exception e) {
            //如果沒有員編則給1234
            emp_pwd = "MTIzNA==";
        }
        agent1_id = agent1_id.trim().equals("") ? "0" : agent1_id.trim(); // 代理人如果沒資料，則補0
        dept_id2 = dept_id2.trim().equals("") ? "0" : dept_id2.trim(); // 部門2如果沒資料，則補0

        String sql = "INSERT INTO Employee (emp_no, emp_name, emp_active, emp_pwd, emp_rfid, "
                + "agent1_id, emp_priority, dept_id1, dept_id2) "
                + "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?);";
        // 建立Object[]給下面rowCount用
        Object[] args = {
            emp_no, emp_name, emp_active, emp_pwd, emp_rfid, agent1_id, emp_priority, dept_id1, dept_id2
        };
        int rowCount = jdbcTemplate.update(sql, args);
        return rowCount; // rowCount 表示異動筆數
    }

    // 變更Active
    public int changeActive(String empNo, int active) {
        String sql = "UPDATE Employee SET emp_active = ? WHERE emp_no = ?";
        int rowCount = jdbcTemplate.update(sql, new Object[]{active, empNo});
        return rowCount; // rowCount 表示異動筆數
    }

    // 變更密碼
    public int changePwd(String empNo, String oldPwd, String newPwd) {
        try {
            final byte[] oldPwdByte = oldPwd.getBytes("UTF-8");
            final byte[] newPwdByte = newPwd.getBytes("UTF-8");
            //編碼
            final String encodedOldPwd = encoder.encodeToString(oldPwdByte);
            final String encodedNewPwd = encoder.encodeToString(newPwdByte);

            String sql = "UPDATE Employee SET emp_pwd = ? WHERE emp_no = ? AND emp_pwd = ?";
            int rowCount = jdbcTemplate.update(sql, new Object[]{encodedNewPwd, empNo, encodedOldPwd});
            return rowCount; // rowCount 表示異動筆數  
        } catch (Exception e) {
        }
        return 0;
    }

    public Integer getIdByNo(String empNo) {
        String sql = "SELECT emp_id FROM Employee Where emp_no = ?";
        try {
            return (Integer) jdbcTemplate.queryForObject(sql, new Object[]{empNo}, Integer.class);
        } catch (Exception e) {
        }
        return null;
    }

    public String getNameByNo(String empNo) {
        String sql = "SELECT emp_name FROM Employee Where emp_no = ? and emp_active=1";
        try {
            return (String) jdbcTemplate.queryForObject(sql, new Object[]{empNo}, String.class);
        } catch (Exception e) {
        }
        return null;
    }

    public Employee login(String username, String password) {
        try {
            String sql = "SELECT * FROM Employee Where emp_no = ? and emp_pwd = ? and emp_active = 1";
            final byte[] passwordByte = password.getBytes("UTF-8");
            //編碼
            password = encoder.encodeToString(passwordByte);
            return jdbcTemplate.queryForObject(sql, new Object[]{username, password}, new BeanPropertyRowMapper<>(Employee.class));
        } catch (Exception e) {
        }
        return null;
    }

    // 查詢所有員工資料(在職)
    public List<Employee> query() {
        String sql = "SELECT * FROM Employee WHERE emp_active=1 Order by dept_id2, emp_no";
        try {
            return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Employee.class));
        } catch (Exception e) {
        }
        return null;
    }

    // 查詢所有員工資料
    public List<Employee> queryAll() {
        String sql = "SELECT * FROM Employee Order by dept_id2, emp_no";
        try {
            return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Employee.class));
        } catch (Exception e) {
        }
        return null;
    }

    // 根據empNo查找該筆員工資料
    public Employee getEmployeeByEmpNo(String empNo) {
        String sql = "SELECT * FROM Employee WHERE emp_no=?";
        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{empNo}, new BeanPropertyRowMapper<>(Employee.class));
        } catch (Exception e) {
        }
        return null;
    }
    
    // 根據empId查找該筆員工資料
    public Employee getEmployeeByEmpId(Integer empId) {
        String sql = "SELECT * FROM Employee WHERE emp_id=?";
        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{empId}, new BeanPropertyRowMapper<>(Employee.class));
        } catch (Exception e) {
        }
        return null;
    }
    
    // 修改員工
    public int update(String emp_no, String emp_name, String emp_active, String emp_rfid, 
                    String agent1_id, String emp_priority, String dept_id1, String dept_id2) {
        agent1_id = agent1_id.trim().equals("") ? "0" : agent1_id.trim();
        dept_id2 = dept_id2.trim().equals("") ? "0" : dept_id2.trim();
                
        String sql = "UPDATE Employee SET emp_name=?, emp_active=?, emp_rfid=?, " +
                     "agent1_id=?, emp_priority=?, dept_id1=?, dept_id2=? " +
                     "WHERE emp_no=?";
        Object[] args = {
            emp_name, emp_active, emp_rfid, 
            agent1_id, emp_priority, dept_id1, dept_id2, emp_no
        };
        int rowCount = jdbcTemplate.update(sql, args);
        return rowCount; // rowCount 表示異動筆數 
    }

}
