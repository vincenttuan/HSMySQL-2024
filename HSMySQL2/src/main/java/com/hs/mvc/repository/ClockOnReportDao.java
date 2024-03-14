package com.hs.mvc.repository;

import com.hs.mvc.entity.ClockOn;
import com.hs.mvc.entity.Status;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ClockOnReportDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    public void updateMemo(String empNo, String memo) {
        if (memo != null && memo.length() > 1) {
            //memo = memo.replaceAll("\\<.*?\\>", "");
            memo = memo.replaceAll("&lt;", "&amp;lt;");
            memo = memo.replaceAll("&gt;", "&amp;gt;");
            memo = memo.replaceAll("<", "&lt;");
            memo = memo.replaceAll(">", "&gt;");
        }
        String sql = "UPDATE clockonreport SET memo = ? "
                   + "WHERE report_date = DATE(CURRENT_DATE) AND emp_no = ?";
        jdbcTemplate.update(sql, memo, empNo);
        
    }
    
    public String getTodayMemo(String empNo) {
        String sql = "SELECT memo FROM clockonreport "
                   + "WHERE report_date = DATE(CURRENT_DATE) AND emp_no = ?";
        String memo = "";
        try {
            memo = jdbcTemplate.queryForObject(sql, String.class, empNo);
        } catch (Exception e) {
        }
        if(memo != null) {
            memo = memo.replaceAll("\\<.*?\\>", "");
        }
        return memo;
    }
    
    // 主管內容回覆
    public int replyUpdate(Date reportDate, String empNo, int replyScore, String replyContent, String replyEmpno) {
        String sql = "UPDATE clockonreport SET reply_score=?, reply_content=?, reply_empno=?, reply_date=now() "
                   + "WHERE report_date = DATE(?) AND emp_no = ?";
        return jdbcTemplate.update(sql, replyScore, replyContent, replyEmpno, reportDate, empNo);
    }
    
    // 主管考核回覆
    public int assessmentUpdate(Date reportDate, String empNo, int assessmentScore, String assessmentContent, String assessmentEmpno) {
        String sql = "UPDATE clockonreport SET assessment_score=?, assessment_content=?, assessment_empno=?, assessment_date=now() "
                   + "WHERE report_date = DATE(?) AND emp_no = ?";
        return jdbcTemplate.update(sql, assessmentScore, assessmentContent, assessmentEmpno, reportDate, empNo);
    }
}
