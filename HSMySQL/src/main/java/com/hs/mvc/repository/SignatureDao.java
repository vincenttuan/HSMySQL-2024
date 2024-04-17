package com.hs.mvc.repository;

import com.hs.mvc.entity.ClockOnException;
import com.hs.mvc.entity.Signature;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class SignatureDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void saveSignature(Signature signature) {
        String sql = "DELETE FROM Signature WHERE emp_no = ? AND sign_type= ? AND sign_year = ? AND sign_month = ?";
        jdbcTemplate.update(sql, signature.getEmpNo(), signature.getSignType(), signature.getSignYear(), signature.getSignMonth());
        
        sql = "Insert Into Signature(emp_no, sign_type, sign_year, sign_month, sign_image) values(?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, signature.getEmpNo(), signature.getSignType(), signature.getSignYear(), signature.getSignMonth(), signature.getSignImage());
    }
    
    public Signature getSignature(String empNo, int type, int year, int month) {
        String sql = "SELECT * FROM signature WHERE emp_no = ? AND sign_type= ? AND sign_year = ? AND sign_month = ?";
        Signature signature = new Signature();
        try {
            signature = jdbcTemplate.queryForObject(sql, new Object[]{empNo, type, year, month}, new BeanPropertyRowMapper<>(Signature.class));
        } catch (Exception e) {
        }
        return signature;            
    }
    
    public List<ClockOnException> queryClockOnException(String empNo, int year, int month) {
        String sql = "SELECT * FROM clockonexception WHERE emp_no = ? AND YEAR(report_date) = ? AND MONTH(report_date) = ?";
        List<ClockOnException> list = new ArrayList<>();
        try {
            list = jdbcTemplate.query(sql, new Object[]{empNo, year, month}, new BeanPropertyRowMapper<>(ClockOnException.class));
        } catch (Exception e) {
        }
        return list;
    }
    
    public void saveClockOnException(ClockOnException ce) {
        String sql = "DELETE FROM clockonexception WHERE emp_no = ? AND report_date= ?";
        jdbcTemplate.update(sql, ce.getEmpNo(), ce.getReportDate());
        
        sql = "Insert Into clockonexception(emp_no, author_empno, report_date, exception_memo, exception_check) values(?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, ce.getEmpNo(), ce.getAuthorEmpno(), ce.getReportDate(), ce.getExceptionMemo(), ce.isExceptionCheck());
    }


}
